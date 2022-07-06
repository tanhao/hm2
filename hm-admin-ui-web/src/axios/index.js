import axios from 'axios'
import Vue from 'vue'

let region = process.env.VUE_APP_NACOS_REGION || 'test';
let baseURL = process.env.VUE_APP_BASE_URL || '';
let service = {
    'hm2-service-user': region,
    'hm2-service-ctrl': region,
    'hm2-service-result-receive': region,
    'hm2-service-order': region,
    'hm2-service-logs': region,
};

class HttpRequest {
    constructor(baseUrl = baseURL) {
        this.baseUrl = baseUrl
        this.queue = {}
    }

    getInsideConfig() {
        const token = sessionStorage.getItem("token");
        const config = {
            baseURL: baseURL,
            headers: {
                'Cache-Control':'no-cache',
                'Pragma':'no-cache',
                'n-d-region': JSON.stringify(service),
                'uid': token
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
            if (!data.success) {
                let vue = new Vue();
                if (data.code === 10001) {
                    vue.$swal({
                            allowOutsideClick:false,
                            title: "账号已登出！",
                            icon: 'error',
                            confirmButtonText: '确定'
                        }
                    ).then((res) => {
                        if (res.value) {
                            this.logout();
                        }
                    })
                }
            }
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
    logout() {
        // 清除token信息并跳转到登录页面
        sessionStorage.clear();
        window.location.href = "/"
    }

    request(options) {
        const instance = axios.create()
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
