# import MySQLdb

# conn = MySQLdb.connect(
#   user = "root",
#   passwd = "1234",
#   host = "127.0.0.1",
#   db = "mooDB",
#   charset = "utf8"
# )

# cur = conn.cursor() # DB에서 SQL문을 실행하거나 실행된 결과를 돌려받는 통로역할.

# cur.execute("""CREATE TABLE IF NOT EXISTS Products ( 
#     id INT AUTO_INCREMENT PRIMARY KEY, 
#     Brand VARCHAR(255), 
#     ProductName VARCHAR(255),
#     Price DECIMAL(10, 2),
#     DiscountRate DECIMAL(5, 2),
#     OriginalPrice DECIMAL(10, 2),
#     ProductURL TEXT,
#     ImageURL TEXT
# );""")

# # cur.execute("insert into products values()") # 데이터 Insert,이 작업 반복해서 채우면 됨


# conn.commit()

# conn.close()

import MySQLdb

def get_db_connection():
    conn = MySQLdb.connect(
        user="root",
        passwd="1234",
        host="127.0.0.1",
        db="mooDB",
        charset="utf8"
    )
    cur = conn.cursor()
    return conn, cur

def create_table():
    conn, cur = get_db_connection()
    
    # DB에서 SQL문을 실행하거나 실행된 결과를 돌려받는 통로역할.
    cur.execute("""CREATE TABLE IF NOT EXISTS Products (  
        id INT AUTO_INCREMENT PRIMARY KEY, 
        Brand VARCHAR(255), 
        ProductName VARCHAR(255),
        Price VARCHAR(255),
        DiscountRate VARCHAR(255),
        OriginalPrice VARCHAR(255),
        ProductURL TEXT,
        ImageURL TEXT
    );""")
    # 크롤링한 데이터들을 cur.execute(INSERT INTO VALUES) 로 넣으면됨

    conn.commit()
    conn.close()

