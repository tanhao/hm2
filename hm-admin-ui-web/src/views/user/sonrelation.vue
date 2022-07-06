<template>
    <div>
        <div class="mb10">
            <a-row>
                <span class="maintxt mlr10">代理会员账号:</span>
                <a-input style="width: 130px" size="small" placeholder="用户名" v-model="username"/>
                <a-button type="primary" icon="search" size="small" class="mlr10"
                          @click="searchRelation(username,false)">
                    查询
                </a-button>

                <span class="maintxt mlr10">代理子账号:</span>
                <a-input style="width: 130px" size="small" placeholder="用户名" v-model="sonname"/>
                <a-button type="primary" icon="search" size="small" class="mlr10" @click="searchRelation(sonname,true)">
                    查询
                </a-button>

                <a-button size="small" class="mlr10 fr" @click="searchList=[]">
                    清除查询
                </a-button>
            </a-row>
            <a-divider dashed/>

            <a-row v-for="(item,i) in searchList" :key="i" class="mlr10">
                <span v-for="(tab,i2) in item" :key="i2">
                    ->{{ $t('AGENT' + tab.l) }}
                     <a-popover v-if="tab.s" placement="bottomLeft" title="子账号">
                        <template slot="content">
                            <p v-for="s in tab.s.split(',')">{{ s }}</p>
                        </template>
                        <a type="primary">
                            {{ tab.u }}
                        </a>
                    </a-popover>
                    <span v-else>{{ tab.u }}</span>
                </span>
                <a-divider/>
            </a-row>
        </div>
    </div>
</template>

<script>
export default {
    name: "sonrelation",
    data() {
        return {
            username: '',
            sonname: '',
            searchList: []
        }
    },
    methods: {
        searchRelation(username, son) {
            if(username) {
                this.$api.user.relation({username, son}).then(res => {
                    if (res.success) {
                        let r = JSON.parse(res.data);
                        if (r) this.searchList.push(r)
                    }
                }).finally(e => {
                    this.username = '';
                    this.sonname = '';
                })
            }
        }
    },
    mounted() {

    }
}
</script>

<style scoped>

</style>