<template>
    <div>
        <a-layout-header>
            <!--头部开始-->
            <div class="mainhead">
                <div class="content">
                    <div class="logotxt">{{companyName}}</div>
                    <ul class="ChannelMenuItems">
                        <li v-for="menu of menuItems" :class="{'active':checkPath(menu)}">
                            <a>
                                <span @click="gotoNav(menu)">{{menu.name}}</span>
                            </a>
                        </li>
                        <li style="float: right">
                            <div class="Announce">
                                <div> {{$t('AGENT'+userInfo.userLevel)}}账号:</div>
                                <span class="mlr10">{{userInfo.username}}</span>
                                <div @click="logout" class="Exit fontwe" alt="安全退出">登出</div>
                            </div>
                        </li>
                    </ul>
                    <div class="SubMenu">
                        <div v-for="menu of menuItems">
                            <ul v-if="checkPath(menu)">
                                <li v-for="children of menu.children"
                                    :class="{'subactive':checkPath(children)}">
                                    <a @click="gotoNavSub(children)">{{children.name}}</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </a-layout-header>

    </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'

export default {
    name: "main-header",
    inject: ['reloadRouter'],
    data() {
        return {
            menuItems: this.$store.state.user.menuItems,
            userInfo: this.$store.state.user.info,
            currentPath: this.$route.path,
            companyName: '189'
        }
    },
    computed: {
        checkPath(menu) {
            return (menu) => {
                let path = menu.path;
                let currentPath = this.$route.path;
                let menuFast = "/" + currentPath.split("/")[1];
                return (currentPath === path || menuFast === path || currentPath === menu.url)
            }
        }
    },
    methods: {
        ...mapActions(['setLogout']),
        logout() {
            this.$swal({
                position: 'top-end',
                title: '确定登出系统?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '是',
                cancelButtonText: '取消'
            }).then((res) => {
                if (res.value) {
                    this.$api.user.logout().then(res => {
                        if (res.success) {
                            this.setLogout();
                            window.location.href = "/"
                        }
                    });
                }
            })
        },
        gotoNav(menu) {
            let path;
            if (menu.children.length > 0) {
                path = menu.children[0].url;
            } else {
                path = menu.path;
            }
            //如果有子路由跳转第一个子路由
            this.reloadData(path);
            this.$router.push({path});
        },
        gotoNavSub(menu) {
            this.reloadData(menu.url);
            this.$router.push({path: menu.url});
        },
        reloadData(path) {
            if (this.$route.path === path) {
                this.reloadRouter();
            }
        },
        loadSitename() {
            this.$api.user.getSitename().then(res => {
                if (res.success) {
                    if (res.data) {
                        this.companyName = res.data;
                    }
                    window.document.title = `系统管理 - ${this.companyName||''}`;
                }
            })
        },
    },
    mounted() {
        this.loadSitename();
    }
}
</script>

<style scoped>

</style>
