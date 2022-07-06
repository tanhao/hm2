import Vue from 'vue'
import VueI18n from 'vue-i18n'
import zh from './lang/zh'
import en from './lang/en'
import store from '../vuex/store.js'
Vue.use(VueI18n);

const i18n = new VueI18n({
  locale: sessionStorage.getItem("lang")||'zh-CN', // set locale
  fallbackLocale: 'zh-CN',
  messages:{
    'zh-CN':{
      ...zh,
    },
    'en-US':{
      ...en,
    }
  }
});

export default i18n
