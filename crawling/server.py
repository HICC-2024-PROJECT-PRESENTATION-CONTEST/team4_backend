from flask import Flask, request, jsonify
from crawl import crawl_category_page, get_recommand

app = Flask(__name__)

@app.route('/crawl', methods=['POST'])
def crawl():
    # 크롤링 함수 실행
    category_urls = request.json.get('category_urls', {})
    results = {}
    for category, url in category_urls.items():
        results[category] = crawl_category_page(url)
    return jsonify(results)

@app.route('/recommend', methods=['POST'])
def recommend():
    product_name = request.json.get('product_name')
    recommendations = get_recommand(product_name)
    return jsonify(recommendations)

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
