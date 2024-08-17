import time
import requests
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
from DB import create_table, insert_or_update_products
from recommand import get_recommand

def scrape_page(driver):
    """현재 페이지의 데이터를 크롤링하여 리스트로 반환."""
    data = []

    # 페이지 소스를 가져와 BeautifulSoup로 파싱
    html_content = driver.page_source
    soup = BeautifulSoup(html_content, 'html.parser')

    # 원하는 요소 찾기
    items_1 = soup.find_all('div', class_='category__sc-rb2kzk-10')  # brand, product_name, price, discount_rate, original_price, likes 값들 있음
    items_2 = soup.find_all('div', class_='category__sc-rb2kzk-5 hWLdIX')  # product_url, image_url 값들 있음
    category = soup.find('h2', class_='common-layout__sc-7w463u-2 kEZePZ').text

    # 웹 페이지에서 원하는 데이터 추출
    for item_1, item_2 in zip(items_1, items_2):
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

            data.append([category, brand, product_name, price, discount_rate, original_price, product_url, image_url])
        except Exception as e:
            continue

    return data

def crawl_category_page(url ,scroll_count=float('inf')):
    """주어진 URL의 카테고리 페이지를 크롤링하여 데이터베이스에 저장."""
    # 웹 드라이버 설정
    driver = webdriver.Chrome()
    driver.get(url)

    # 자바스크립트 로딩을 기다리기 위해 잠시 대기
    time.sleep(3)

    # 현재 높이를 가져오는 함수
    last_height = driver.execute_script("return document.body.scrollHeight")

    all_data = []

    for i in range(scroll_count):
        # END 키를 사용하여 스크롤
        driver.find_element(By.TAG_NAME, "body").send_keys(Keys.END)
        time.sleep(0.7)
        
        # 현재 페이지에서 데이터 수집
        page_data = scrape_page(driver)
        all_data.extend(page_data)

        # 새로운 높이를 가져옴
        new_height = driver.execute_script("return document.body.scrollHeight")

        # 이전 높이와 새로운 높이가 같으면 더 이상 스크롤할 수 없음
        if new_height == last_height:
            print("*******break********")
            break

        last_height = new_height

    driver.quit()

    # 중복 제거
    # all_data = list(map(list, set(map(tuple, all_data))))

    # 데이터베이스에 저장
    create_table()
    insert_or_update_products(all_data)

    return all_data


# category_urls에 저장된 모든 URL을 크롤링하는 코드
category_urls = {
    "상의": "https://www.musinsa.com/categories/item/001?device=mw",
    "아우터": "https://www.musinsa.com/categories/item/002?device=mw",
    "바지": "https://www.musinsa.com/categories/item/003?device=mw",
    "원피스/스커트": "https://www.musinsa.com/categories/item/100?device=mw",
    "신발": "https://www.musinsa.com/categories/item/103?device=mw",
    "가방": "https://www.musinsa.com/categories/item/004?device=mw",
    "패션소품": "https://www.musinsa.com/categories/item/101?device=mw",
    "속옷/홈웨어": "https://www.musinsa.com/categories/item/026?device=mw",
    "뷰티": "https://www.musinsa.com/categories/item/104?device=mw",
    "디지털/라이브": "https://www.musinsa.com/categories/item/102?device=mw",
    "키즈": "https://www.musinsa.com/categories/item/106?device=mw"
}

# 딕셔너리 순회하며 각 URL로 크롤링 함수 호출
for category, url in category_urls.items():
    print(f"크롤링 중: {category}")
    
    # 크롤링 함수 호출 (각 카테고리마다 10번 스크롤)
    # 테스트용으로 작게주고, 디폴트는 다긁어옴
    data = crawl_category_page(url, 3)
    print(f"{category} 크롤링 완료!\n") 


# 특정 제품과 유사한 제품 추천
# recommended_products = get_recommand(dbdata, 'TBLF 맨투맨 네이비', 30) # dbdata엔 전체 db데이터가 2차원 리스트 형태로 들어가야할듯  
# print(recommended_products)
