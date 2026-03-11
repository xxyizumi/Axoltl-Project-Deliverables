/**
 * CSRFトークン取得・設定ヘルパー
 */
const CsrfHelper = {
    /**
     * メタタグからCSRFトークンを取得
     */
    getToken() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.getAttribute('content') : null;
    },
    
    /**
     * メタタグからCSRFヘッダー名を取得
     */
    getHeaderName() {
        const meta = document.querySelector('meta[name="_csrf_header"]');
        return meta ? meta.getAttribute('content') : 'X-CSRF-TOKEN';
    },
    
    /**
     * fetch用のヘッダーオブジェクトを生成
     */
    getHeaders(additionalHeaders = {}) {
        return {
            [this.getHeaderName()]: this.getToken(),
            ...additionalHeaders
        };
    },
    
    /**
     * CSRFトークン付きのfetchを実行
     */
    async fetch(url, options = {}) {
        const headers = {
            ...this.getHeaders(),
            ...(options.headers || {})
        };
        
        return fetch(url, {
            ...options,
            headers
        });
    }
};

// 使用例:
// CsrfHelper.fetch('/api/data', {
//     method: 'POST',
//     headers: { 'Content-Type': 'application/json' },
//     body: JSON.stringify({ key: 'value' })
// });