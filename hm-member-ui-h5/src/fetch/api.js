import axios from 'axios'
import store from '../vuex/store.js'
import {MessageBox} from 'mint-ui'
import router from '@/router'
import Mint from 'mint-ui';

//全局文件获取请求路径
axios.defaults.baseURL = process.env.baseUrl;

const region = process.env.region;

// http request 拦截器
axios.interceptors.request.use(
  config => {
    let token = store.state.member.token;
    if (token) {
      //config.headers['Authorization'] = "Bearer "+token.jwt_token;
      config.headers['uid'] = token;
    }
    let service = {
      'hm2-service-user': region,
      'hm2-service-ctrl': region,
    }
    config.headers['n-d-region'] = JSON.stringify(service);
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);

// http response 拦截器
axios.interceptors.response.use(
  response => {
    if (response) {
      if (!response.data || response.data.code == 10001) {
        store.dispatch('setPromptInformation', "此账号在别处登录!");
        /*store.state.member.member = null;*/
        store.dispatch('loginSuccess', null);
        router.replace({path: "/", replace: true});
      } else {
        return response;
      }
    } else {
      router.replace({path: "/", replace: true});
    }

  },
  error => {
    return Promise.reject(error)   // 返回接口返回的错误信息
  }
);

function fetch(url, params, method, timeout) {
  return new Promise((resolve, reject) => {
    let cfg = {
      method: method,
      url: url,
      data: params,
      timeout: timeout
    };
    if (method === 'get') {
      cfg.params = params;
    }
    axios(cfg).then(response => {
      resolve(response.data);
    }).catch(error => {
      reject(error);
    });
  })
}

export default fetch;
