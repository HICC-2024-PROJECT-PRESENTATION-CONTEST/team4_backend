import MySQLdb
import numpy as np
import pandas as pd
import json

def get_db_connection():
    conn = MySQLdb.connect(
        user="root",
        passwd="1234",
        host="127.0.0.1",
        db="team4",
        charset="utf8"
    )
    cur = conn.cursor()
    return conn, cur

def create_table():
    conn, cur = get_db_connection()
    
    # DB에서 SQL문을 실행하거나 실행된 결과를 돌려받는 통로역할.
    cur.execute("""CREATE TABLE IF NOT EXISTS product (  
        Category VARCHAR(255),
        Brand VARCHAR(255), 
        ProductName VARCHAR(255) PRIMARY KEY,
        Price VARCHAR(255),
        DiscountRate VARCHAR(255),
        OriginalPrice VARCHAR(255),
        ProductURL TEXT,
        ImageURL TEXT
    );""")
    # 크롤링한 데이터들을 cur.execute(INSERT INTO VALUES) 로 넣으면됨

    conn.commit()
    conn.close()

def insert_or_update_products(products):
    conn, cur = get_db_connection()
    
    query = """
    INSERT INTO product (Category, Brand, ProductName, Price, DiscountRate, OriginalPrice, ProductURL, ImageURL)
    VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
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
    

def get_all_data():
    conn, cur = get_db_connection()

    query = "SELECT * FROM product;"

    # pandas의 read_sql() 함수를 사용하여 쿼리 결과를 DataFrame으로 변환
    df = pd.read_sql(query, conn)

    conn.commit()
    conn.close()
    
    return df


def convert_price(price_str):    # 가격 문자열에서 숫자만 추출하여 정수로 변환하는 함수
    # 가격 문자열에서 숫자와 쉼표를 제거
    return int(price_str.replace('원', '').replace(',', '').strip())

def send_products(products):
    product_keys = ["category", "brand", "productName", "price", "discountRate", "originalPrice", "productURL", "imageURL"]
    
    product_dicts = []
    for product in products:
        # 가격 필드 변환
        product[3] = convert_price(product[3])
        product[5] = convert_price(product[5])
        
        # 리스트를 딕셔너리로 변환
        product_dict = dict(zip(product_keys, product))
        product_dicts.append(product_dict)
    
    return product_dicts
