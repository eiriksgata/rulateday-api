(function(e){function n(n){for(var r,o,u=n[0],i=n[1],l=n[2],f=0,d=[];f<u.length;f++)o=u[f],Object.prototype.hasOwnProperty.call(a,o)&&a[o]&&d.push(a[o][0]),a[o]=0;for(r in i)Object.prototype.hasOwnProperty.call(i,r)&&(e[r]=i[r]);h&&h(n);while(d.length)d.shift()();return c.push.apply(c,l||[]),t()}function t(){for(var e,n=0;n<c.length;n++){for(var t=c[n],r=!0,o=1;o<t.length;o++){var u=t[o];0!==a[u]&&(r=!1)}r&&(c.splice(n--,1),e=i(i.s=t[0]))}return e}var r={},o={app:0},a={app:0},c=[];function u(e){return i.p+"js/"+({}[e]||e)+"."+{"chunk-214553ba":"3bfeb217","chunk-216f9746":"ce461bdc","chunk-09220e72":"52328c80","chunk-254809a3":"148425f5","chunk-294c18e6":"92ec27e9","chunk-2d0d63f1":"ffca847f","chunk-5be0e972":"6fad0e60","chunk-6f2db4f4":"a1c8c382","chunk-7fcb823b":"27ed8ba5","chunk-a94ae7e2":"dc905be3","chunk-ce316d2a":"94871d4c","chunk-21700b46":"62d671ee"}[e]+".js"}function i(n){if(r[n])return r[n].exports;var t=r[n]={i:n,l:!1,exports:{}};return e[n].call(t.exports,t,t.exports,i),t.l=!0,t.exports}i.e=function(e){var n=[],t={"chunk-09220e72":1,"chunk-254809a3":1,"chunk-294c18e6":1,"chunk-5be0e972":1,"chunk-7fcb823b":1,"chunk-a94ae7e2":1,"chunk-ce316d2a":1};o[e]?n.push(o[e]):0!==o[e]&&t[e]&&n.push(o[e]=new Promise((function(n,t){for(var r="css/"+({}[e]||e)+"."+{"chunk-214553ba":"31d6cfe0","chunk-216f9746":"31d6cfe0","chunk-09220e72":"008e83ea","chunk-254809a3":"b46e2d51","chunk-294c18e6":"cb1d0ee5","chunk-2d0d63f1":"31d6cfe0","chunk-5be0e972":"24acad6f","chunk-6f2db4f4":"31d6cfe0","chunk-7fcb823b":"aace9694","chunk-a94ae7e2":"d9967467","chunk-ce316d2a":"7bcd0f2e","chunk-21700b46":"31d6cfe0"}[e]+".css",a=i.p+r,c=document.getElementsByTagName("link"),u=0;u<c.length;u++){var l=c[u],f=l.getAttribute("data-href")||l.getAttribute("href");if("stylesheet"===l.rel&&(f===r||f===a))return n()}var d=document.getElementsByTagName("style");for(u=0;u<d.length;u++){l=d[u],f=l.getAttribute("data-href");if(f===r||f===a)return n()}var h=document.createElement("link");h.rel="stylesheet",h.type="text/css",h.onload=n,h.onerror=function(n){var r=n&&n.target&&n.target.src||a,c=new Error("Loading CSS chunk "+e+" failed.\n("+r+")");c.code="CSS_CHUNK_LOAD_FAILED",c.request=r,delete o[e],h.parentNode.removeChild(h),t(c)},h.href=a;var s=document.getElementsByTagName("head")[0];s.appendChild(h)})).then((function(){o[e]=0})));var r=a[e];if(0!==r)if(r)n.push(r[2]);else{var c=new Promise((function(n,t){r=a[e]=[n,t]}));n.push(r[2]=c);var l,f=document.createElement("script");f.charset="utf-8",f.timeout=120,i.nc&&f.setAttribute("nonce",i.nc),f.src=u(e);var d=new Error;l=function(n){f.onerror=f.onload=null,clearTimeout(h);var t=a[e];if(0!==t){if(t){var r=n&&("load"===n.type?"missing":n.type),o=n&&n.target&&n.target.src;d.message="Loading chunk "+e+" failed.\n("+r+": "+o+")",d.name="ChunkLoadError",d.type=r,d.request=o,t[1](d)}a[e]=void 0}};var h=setTimeout((function(){l({type:"timeout",target:f})}),12e4);f.onerror=f.onload=l,document.head.appendChild(f)}return Promise.all(n)},i.m=e,i.c=r,i.d=function(e,n,t){i.o(e,n)||Object.defineProperty(e,n,{enumerable:!0,get:t})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,n){if(1&n&&(e=i(e)),8&n)return e;if(4&n&&"object"===typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(i.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&n&&"string"!=typeof e)for(var r in e)i.d(t,r,function(n){return e[n]}.bind(null,r));return t},i.n=function(e){var n=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(n,"a",n),n},i.o=function(e,n){return Object.prototype.hasOwnProperty.call(e,n)},i.p="",i.oe=function(e){throw console.error(e),e};var l=window["webpackJsonp"]=window["webpackJsonp"]||[],f=l.push.bind(l);l.push=n,l=l.slice();for(var d=0;d<l.length;d++)n(l[d]);var h=f;c.push([0,"chunk-vendors"]),t()})({0:function(e,n,t){e.exports=t("cd49")},cd49:function(e,n,t){"use strict";t.r(n);t("e260"),t("e6cf"),t("cca6"),t("a79d");var r=t("2b0e"),o=(t("d3b7"),t("bc3a")),a=t.n(o),c=(t("3ca3"),t("ddb0"),t("8c4f"));r["default"].use(c["a"]);var u=[{path:"/error/403",name:"page403",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-09220e72")]).then(t.bind(null,"dc17"))}},{path:"/error/404",name:"page404",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-5be0e972")]).then(t.bind(null,"d567"))}},{path:"/error/500",name:"page500",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-a94ae7e2")]).then(t.bind(null,"5404"))}},{path:"/",name:"login",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-6f2db4f4")]).then(t.bind(null,"ede4"))}},{path:"/dashboard",name:"dashboard",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-2d0d63f1")]).then(t.bind(null,"7277"))},children:[{path:"/home",name:"home",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-294c18e6")]).then(t.bind(null,"ba72"))}},{path:"/lib/infinite",name:"Infinite",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-254809a3")]).then(t.bind(null,"a7dd"))}},{path:"/navigation/create/infinite",name:"InfiniteCreateNavigation",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-7fcb823b")]).then(t.bind(null,"c595"))}},{path:"/feedback/list",name:"FeedbackList",component:function(){return t.e("chunk-214553ba").then(t.bind(null,"a378"))}},{path:"/feedback/submit",name:"FeedbackSubmit",component:function(){return t.e("chunk-21700b46").then(t.bind(null,"22d1"))}},{path:"/games/event",name:"GamesEvent",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-ce316d2a")]).then(t.bind(null,"4617"))}}]},{path:"/",name:"dashboard",component:function(){return Promise.all([t.e("chunk-216f9746"),t.e("chunk-2d0d63f1")]).then(t.bind(null,"7277"))}}],i=new c["a"]({routes:u}),l=i,f={},d=a.a.create(f);d.interceptors.request.use((function(e){return e.headers={Authorization:localStorage.getItem("token")},e}),(function(e){return Promise.reject(e)})),d.interceptors.response.use((function(e){return localStorage.setItem("token",e.headers.authorization),10002==e.data.code&&l.push("/"),e}),(function(e){return Promise.reject(e)}));var h={install:function(e){e.$axios=d}};h.install=function(e){e.$axios=d,window.axios=d,Object.defineProperties(e.prototype,{$axios:{get:function(){return d}}})},r["default"].use(h);var s=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("v-app",[t("v-main",{staticStyle:{"background-color":"#bbe6d6"}},[t("router-view")],1)],1)},p=[],b=r["default"].extend({name:"App",data:function(){return{}}}),m=b,k=t("2877"),v=t("6544"),g=t.n(v),y=t("7496"),P=t("f6c4"),w=Object(k["a"])(m,s,p,!1,null,null,null),j=w.exports;g()(w,{VApp:y["a"],VMain:P["a"]});var x=t("f309"),O=t("2db4"),S=t("8336"),C=t("132d"),E=t("ffa5"),_=t.n(E);t("d4b8"),t("5363");r["default"].use(x["a"],{components:{VSnackbar:O["a"],VBtn:S["a"],VIcon:C["a"]}}),r["default"].use(_.a,{x:"right",y:"top",color:"info",iconColor:"",classes:["body-2"],timeout:3e3,dismissable:!0,multiLine:!1,vertical:!1,queueable:!1,showClose:!1,closeText:"",closeIcon:"close",closeColor:"",slot:[],shorts:{custom:{color:"purple"}},property:"$toast"});var A=new x["a"]({icons:{iconfont:"mdi"}});r["default"].config.productionTip=!1,new r["default"]({router:l,vuetify:A,render:function(e){return e(j)}}).$mount("#app")}});
//# sourceMappingURL=app.cfbaa4d0.js.map