import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './main.scss'
import request from "@/utils/request";
import Authorization from "@/components/Authorization.vue";
import mixins  from "@/mixins";
Vue.mixin(mixins);
Vue.component('Authorization', Authorization);
Vue.prototype.$http = request;
Vue.use(ElementUI);
Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
