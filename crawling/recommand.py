import numpy as np
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neighbors import NearestNeighbors
from DB import get_all_data 


# def get_recommand(x, n=20):
#     df = get_all_data()

#     df['ProductName'] = df['ProductName'].fillna('')

#     def get_items(product_name, n=20):
#         if product_name not in df['ProductName'].values:
#             return "Product not found in the database."
        
#         # 입력된 제품의 인덱스 및 카테고리 추출
#         idx = df[df['ProductName'] == product_name].index[0]
#         target_category = df.loc[idx, 'Category']

#         # 해당 카테고리의 제품만 필터링
#         category_df = df[df['Category'] == target_category].reset_index(drop=True)

#         # 필터링된 데이터에 대해 TF-IDF 계산
#         tfidf = TfidfVectorizer(stop_words='english', max_features=10000)
#         tfidf_matrix = tfidf.fit_transform(category_df['ProductName'])

#         # NearestNeighbors를 사용하여 유사한 제품 검색
#         nn = NearestNeighbors(metric='cosine', algorithm='brute')
#         nn.fit(tfidf_matrix)

#         # 입력된 제품이 필터링된 데이터에서 몇 번째 인덱스인지 확인
#         category_idx = category_df[category_df['ProductName'] == product_name].index[0]

#         # 해당 제품과 가장 유사한 n개의 제품 인덱스 추출
#         distances, indices = nn.kneighbors(tfidf_matrix[category_idx], n_neighbors=n+1)

#         # 자기 자신 제외
#         indices = indices.flatten()[1:]

#         return category_df['ProductName'].iloc[indices][:n]

#     return get_items(x, n)

def get_recommand(product_name, n=20):
    df = get_all_data()

    # 'ProductName' 열의 누락된 값을 채움
    df['ProductName'] = df['ProductName'].fillna('')

    def get_items(product_name, n=20):
        if product_name not in df['ProductName'].values:
            return []

        # 입력된 제품의 인덱스 및 카테고리 추출
        idx = df[df['ProductName'] == product_name].index[0]
        target_category = df.loc[idx, 'Category']

        # 해당 카테고리의 제품만 필터링
        category_df = df[df['Category'] == target_category].reset_index(drop=True)

        # 필터링된 데이터에 대해 TF-IDF 계산
        tfidf = TfidfVectorizer(stop_words='english', max_features=10000)
        tfidf_matrix = tfidf.fit_transform(category_df['ProductName'])

        # NearestNeighbors를 사용하여 유사한 제품 검색
        nn = NearestNeighbors(metric='cosine', algorithm='brute')
        nn.fit(tfidf_matrix)

        # 필터링된 데이터에서 입력된 제품의 인덱스를 확인
        category_idx = category_df[category_df['ProductName'] == product_name].index[0]

        # 해당 제품과 가장 유사한 n개의 제품 인덱스 추출
        distances, indices = nn.kneighbors(tfidf_matrix[category_idx], n_neighbors=n+1)

        # 자기 자신 제외
        indices = indices.flatten()[1:]

        # 추천된 제품의 전체 속성 반환
        recommended_products = category_df.iloc[indices].to_dict(orient='records')
        return recommended_products

    return get_items(product_name, n)

# # 예시 사용
recommended_products = get_recommand('(3pack) 컬러 타비 삭스', 30)
print(recommended_products)
