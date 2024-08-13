import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time
import pandas as pd
from bs4 import BeautifulSoup
import requests
from itertools import repeat
import re
import csv
from DB import create_table, get_db_connection

def crwal():
    # 페이지 소스를 가져와 BeautifulSoup로 파싱
    html_content = driver.page_source
    soup = BeautifulSoup(html_content, 'html.parser')

    # 원하는 요소 찾기
    items_1 = soup.find_all('div', class_='category__sc-rb2kzk-10') # brand, product_name, price, discount_rate, original_price, likes 값들 있음
    items_2 = soup.find_all('div', class_='category__sc-rb2kzk-5 hWLdIX') # product_url, image_url 값들 있음

    # 웹 페이지에서 원하는 데이터 추출
    for item_1, item_2 in zip(items_1,items_2):
        try:
            brand = item_1.find('a', class_='category__sc-rb2kzk-11 kPDCPR').text
            product_name = item_1.find('a', class_='category__sc-rb2kzk-12 gBkfRU').text
            price = item_1.find('span', class_='category__sc-79f6w4-5 eTRmwC').text
            discount_rate = item_1.find('strong', class_='category__sc-79f6w4-9 jNpLBZ').text
            original_price = item_1.find('del', class_='category__sc-79f6w4-6 iHtcSg').text

            # 상품 링크와 이미지 URL 추출
            link_tag = item_2.find('a')
            img_tag = item_2.find('img')
            if link_tag and img_tag:
                product_url = link_tag['href']
                image_url = img_tag['src']
            else:
                print("Link tag or Image tag not found")

            data.append([brand, product_name, price, discount_rate, original_price, product_url, image_url])
        except Exception as e:
            print(f'Error occurred: {e}')
            continue

def insert_or_update_products(products):
    conn, cur = get_db_connection()
    
    query = """
    INSERT INTO Products (Brand, ProductName, Price, DiscountRate, OriginalPrice, ProductURL, ImageURL)
    VALUES (%s, %s, %s, %s, %s, %s, %s)
    ON DUPLICATE KEY UPDATE
    Price = VALUES(Price),
    DiscountRate = VALUES(DiscountRate),
    OriginalPrice = VALUES(OriginalPrice),
    ProductURL = VALUES(ProductURL),
    ImageURL = VALUES(ImageURL);
    """
    
    # Execute insert or update for each product in the list
    for product in products:
        cur.execute(query, product)
    
    conn.commit()
    conn.close()
    

# 웹 드라이버 설정
driver = webdriver.Chrome()
driver.get("https://www.musinsa.com/categories/item/001?device=mw")

# 자바스크립트 로딩을 기다리기 위해 잠시 대기
time.sleep(5)  # 페이지 로딩을 위해 대기

# 현재 높이를 가져오는 함수
last_height = driver.execute_script("return document.body.scrollHeight")

data = []

while True:
    # PAGE_DOWN 키를 사용하여 스크롤
    
    driver.find_element(By.TAG_NAME, "body").send_keys(Keys.END)
    time.sleep(0.7)
    crwal()

    # 새로운 높이를 가져옴
    new_height = driver.execute_script("return document.body.scrollHeight")
    
    # 이전 높이와 새로운 높이가 같으면 더 이상 스크롤할 수 없음
    if new_height == last_height:
        print("*******break********")
        break

    last_height = new_height

create_table()
insert_or_update_products(data)

# # CSV 파일로 저장
# csv_file = 'products.csv'
# csv_header = ['Brand', 'Product Name', 'Price', 'Discount Rate', 'Original Price', 'Product URL','Image URL']

# with open(csv_file, mode='w', newline='', encoding='utf-8') as file:
#     writer = csv.writer(file)
#     writer.writerow(csv_header)
#     writer.writerows(data)

# print(f'Data has been written to {csv_file}')

# 웹 드라이버 종료
driver.quit()
