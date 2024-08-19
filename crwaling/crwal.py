import time
import requests
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
from DB import create_table, insert_or_update_products, get_all_data
from recommand import get_recommand

def scrape_page(driver):
    """현재 페이지의 데이터를 크롤링하여 리스트로 반환."""
    data = []

    # 페이지 소스를 가져와 BeautifulSoup로 파싱
    html_content = driver.page_source
    soup = BeautifulSoup(html_content, 'html.parser')

    # 원하는 요소 찾기
    items_1 = soup.find_all('div', class_='sc-dtBeHJ iFyEFD')  # brand, product_name, price, discount_rate, original_price, likes 값들 있음
    items_2 = soup.find_all('div', class_='sc-eDPFhE gKpBep')  # product_url, image_url 값들 있음
    category = soup.find('span', class_='text-base font-medium font-pretendard').text

    for item_1, item_2 in zip(items_1, items_2):
        try:
            # 브랜드 이름 추출
            brand = item_1.find('span', class_='text-body_13px_semi sc-dcJtft sc-iGgVNO jEEFmT laXDWb font-pretendard')
            brand = brand.text if brand else "N/A"

            # 상품 이름 추출
            product_name = item_1.find('span', class_='text-body_13px_reg sc-dcJtft sc-gsFSjX jEEFmT ecuaTR font-pretendard')
            product_name = product_name.text if product_name else "N/A"

            # 가격 추출
            price = item_1.find('span', class_='text-body_13px_semi sc-fqkwJk ioeSYE font-pretendard')
            price = price.text if price else "N/A"

            # 할인율 추출
            discount_rate = item_1.find('span', class_='text-body_13px_semi sc-fqkwJk ioeSYE text-red font-pretendard')
            discount_rate = discount_rate.text if discount_rate else "N/A"

            # 원래 가격 추출 (기본값 "N/A")
            # original_price = item_1.find('del', class_='category__sc-79f6w4-6 iHtcSg')
            # original_price = original_price.text if original_price else "N/A"
            original_price = "N/A"
            
            # 상품 링크와 이미지 URL 추출
            link_tag = item_2.find('a')
            img_tag = item_2.find('img')
            product_url = link_tag['href'] if link_tag else "N/A"
            image_url = img_tag['src'] if img_tag else "N/A"

            # 수집한 데이터를 리스트에 추가
            data.append([category, brand, product_name, price, discount_rate, original_price, product_url, image_url])

        except Exception as e:
            print(f"Error: {e}")
            continue

    return data

def crawl_category_page(url ,scroll_count=999999):
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

    # 데이터베이스에 저장
    create_table()
    insert_or_update_products(all_data)

    return all_data


# category_urls에 저장된 모든 URL을 크롤링하는 코드
category_urls = {
    "상의": "https://www.musinsa.com/categories/item/001?gf=A",
    "아우터": "https://www.musinsa.com/categories/item/002?gf=A",
    "바지": "https://www.musinsa.com/categories/item/003?gf=A",
    "원피스/스커트": "https://www.musinsa.com/categories/item/100?gf=A",
    "신발": "https://www.musinsa.com/categories/item/103?gf=A",
    "가방": "https://www.musinsa.com/categories/item/004?gf=A",
    "패션소품": "https://www.musinsa.com/categories/item/101?gf=A",
    "속옷/홈웨어": "https://www.musinsa.com/categories/item/026?gf=A",
    "뷰티": "https://www.musinsa.com/categories/item/104?gf=A",
    "스포츠/레저": "https://www.musinsa.com/categories/item/017?gf=A",
    "디지털/라이브": "https://www.musinsa.com/categories/item/102?gf=A",
    "아웃렛": "https://www.musinsa.com/categories/item/107?gf=A",
    "부티크": "https://www.musinsa.com/categories/item/105?gf=A",
    "키즈": "https://www.musinsa.com/categories/item/106?gf=A",
    "어스": "https://www.musinsa.com/categories/item/108?gf=A"

}

# 딕셔너리 순회하며 각 URL로 크롤링 함수 호출
for category, url in category_urls.items():
    print(f"크롤링 중: {category}")
    
    # 크롤링 함수 호출 (각 카테고리마다 n번 스크롤)
    # 테스트용으로 작게주고, 디폴트는 다긁어옴
    crawl_category_page(url,3)
    print(f"{category} 크롤링 완료!\n") 

# 특정 제품과 유사한 제품 추천
recommended_products = get_recommand('소프트 필링 세미오버 라운드 니트 초코브라운', 30) # 전체 DB데이터 내에서 주어진 문자열과 가장 유사성이 높은 상위 n개의 이름 리턴 
print(recommended_products)
