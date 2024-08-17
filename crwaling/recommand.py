import numpy as np
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel

def get_recommand(data, x, n=20): # data : 2차원 리스트 형태의 값, x : str헝태의 상품명 1개(찜목록 중 1)
    df = pd.DataFrame(data, columns=['category', 'brand', 'product_name', 'price', 'discount_rate', 'original_price', 'product_url', 'image_url'])
    
    df['product_name'].isnull().values.any()
    df['product_name'] = df['product_name'].fillna('')

    tfidf = TfidfVectorizer(stop_words = 'english')
    tfidf_matrix = tfidf.fit_transform(df['product_name'])
    cosine_sim = linear_kernel(tfidf_matrix,tfidf_matrix)
    indices = pd.Series(df.index, index=df['product_name']).drop_duplicates()
    
    # 상품명을 입력받으면 코사인 유사도를 통해 가장 유사도가 높은 상위 10개의 목록 반환
    def get_recommand(product_name, n=20 ,cosine_sim = cosine_sim):
        # 상품 이름을 통해 전체 데이터 기준 그 상품의 idx얻기
        idx = indices[product_name]
    
        # 코사인 유사도 매트릭스(cosine_sim) 에서 idx에 해당하는 데이터를 (idx, 유사도) 형태로 얻기
        sim_scores = list(enumerate(cosine_sim[idx]))
    
        # 코사인 유사도 기준으로 내림차순 정렬
        sim_scores = sorted(sim_scores, key=lambda x:x[1], reverse=True)
        sim_socres = sim_scores[1:n+1] # 자기자신 제외한 n개의 항목 슬라이싱
    
        # 추천 항목 n개의 인덱스 정보 추출
        product_indices = [i[0] for i in sim_scores]
    
        return df['product_name'].iloc[product_indices][:n]

    return get_recommand(x, n)
    