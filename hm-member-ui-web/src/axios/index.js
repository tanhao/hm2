import axios from 'axios'
import Vue from 'vue'
import store from '../vuex/store.js'
import Mint from 'mint-ui';
let region = process.env.region || 'test';
let baseURL = process.env.baseUrl || '';
let service = {
  'hm2-service-user': region,
  'hm2-service-ctrl': region,
  'hm2-service-order': region,
  'hm2-service-member': region,
};

class HttpRequest {
  constructor(baseUrl = baseURL) {
    this.baseUrl = baseUrl
    this.queue = {}
  }

  getInsideConfig() {
    let token = store.state.member.token;
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
          Mint.MessageBox({$type:'confirm',message:'账号已登出!',title:'提示',closeOnClickModal:false}).then(action => {
            if (Object.is(action, 'confirm')) {
              if(store.getters.socket && store.getters.socket.ws.readyState == 1) {
                store.dispatch("setCloseSocket");
              }
              window.location.href = '/';
            }
          }).catch((err) => {
            if (Object.is(err, 'cancel')) {
              return;
            }
          })
        }else if(data.code===2024){

          let messageContent = '';

          let obj = JSON.parse(data.data);

          messageContent += '<div class="popnotice">' + data.message + '</div>' + '<div class="popnotice">' + obj.startTime + '-' + obj.endTime + '</div>';
          Mint.MessageBox({$type:'confirm',message:messageContent,title:'提示',closeOnClickModal:false}).then(action => {
          }).catch((err) => {
            if (Object.is(err, 'cancel')) {
              return;
            }
          })
        }
        else if(data.code===2009){

        }else if(data.code===23020 || data.code===888888 || data.code===23005){
          Mint.Toast({
            message: data.message,
            iconClass: 'erroico',
            duration:1500
          })
        }else{
          Mint.MessageBox({$type:'confirm',message:data.message,title:'提示',closeOnClickModal:false}).then(action => {
          }).catch((err) => {
            if (Object.is(err, 'cancel')) {
              return;
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
