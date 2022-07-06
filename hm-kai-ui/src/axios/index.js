import axios from 'axios'

let baseURL = '';

class HttpRequest {
    constructor(baseUrl = baseURL) {
        this.baseUrl = baseUrl
        this.queue = {}
    }

    getInsideConfig() {
        const config = {
            baseURL: baseURL,
            headers: {
                'Cache-Control': 'no-cache',
                'Pragma': 'no-cache',
            }
        };
        return config
    }

    destroy(url) {
        delete this.queue[url]
    }

    interceptors(instance, url) {
        instance.interceptors.request.use(config => {
            this.queue[url] = true;

            return config
        }, error => {
            return Promise.reject(error)
        })
        // 响应拦截
        instance.interceptors.response.use(res => {
            this.destroy(url)
            const {data, status} = res

            return res.data
        }, error => {
            this.destroy(url)
            let errorInfo = error.response
            if (!errorInfo) {
                const {request: {statusText, status}, config} = JSON.parse(JSON.stringify(error))
                errorInfo = {
                    statusText,
                    status,
                    request: {responseURL: config.url}
                }
            }
            return Promise.reject(error)
        })
    }

    request(options) {
        const instance = axios.create();
        options = Object.assign(this.getInsideConfig(), options)
        this.interceptors(instance, options.url)
        return instance(options)
    }

    requestGet(url, params = {}, timeout = 20000) {
        let options = {
            url,
            params,
            method: 'get',
            timeout
        }
        return this.request(options)
    }

    requestPost(url, data = {}, timeout = 20000) {
        let options = {
            url,
            data,
            method: 'post',
            timeout
        }
        return this.request(options)
    }

    requestPostForm(url, data = {}, timeout = 20000) {
        let options = {
            url,
            data,
            method: 'post',
            timeout,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return this.request(options)
    }

    requestPostMult(url, data = {}, timeout = 20000) {
        let options = {
            url,
            data,
            method: 'post',
            timeout,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return this.request(options)
    }

    requestPut(url, data = {}, timeout = 20000) {
        let options = {
            url,
            data,
            method: 'put',
            timeout
        }
        return this.request(options)
    }

    requestDelete(url, data = {}, timeout = 20000) {
        let options = {
            url,
            data,
            method: 'delete',
            timeout
        }
        return this.request(options)
    }

}

export default HttpRequest
