<template>
    <div>
        <a-button-group size="small" v-if="showButton">
            <a-button @click="changeButton(1)" :type="types===1?'primary':''">今日</a-button>
            <a-button @click="changeButton(2)" :type="types===2?'primary':''">昨日</a-button>
            <a-button @click="changeButton(3)" :type="types===3?'primary':''">本周</a-button>
            <a-button @click="changeButton(4)" :type="types===4?'primary':''">上周</a-button>
            <a-button @click="changeButton(5)" :type="types===5?'primary':''">本月</a-button>
            <a-button @click="changeButton(6)" :type="types===6?'primary':''">上月</a-button>
        </a-button-group>

    </div>
</template>
<script>
    export default {
        data() {
            return {
                types: 1,
                startTime: this.todayDate(),
                endTime: this.todayDate(),
            };
        },
        props: {
            showButton: false,
        },
        mounted() {
           this.changePicker();
        },
        methods: {
            disabledDate(current) {
                return (current && current < this.moment().add(-90, 'day').endOf('day')) || current > this.moment().endOf('day');
            },
            changePicker() {
                this.$emit("on-change", [this.moment(this.startTime).format("YYYY-MM-DD"), this.moment(this.endTime).format("YYYY-MM-DD")]);
            },
            changeButton(type) {
                this.types = type;
                let date = new Date();
                if (date.getHours() < 7) {
                    date.setDate(date.getDate() - 1);
                }
                let end = new Date(date);
                let start = new Date(date);
                if (type === 2) {
                    start.setDate(start.getDate() - 1);
                    end.setDate(end.getDate() - 1);
                } else if (type === 3) {
                    start.setDate(
                        start.getDate() - (start.getDay() === 0 ? 7 : start.getDay()) + 1
                    );
                    end.setDate(
                        end.getDate() - (end.getDay() === 0 ? 7 : end.getDay()) + 7
                    );
                } else if (type === 4) {
                    start.setDate(
                        start.getDate() - (start.getDay() === 0 ? 7 : start.getDay()) - 6
                    );
                    end.setDate(end.getDate() - (end.getDay() === 0 ? 7 : end.getDay()));
                } else if (type === 5) {
                    let year = date.getFullYear() + "";
                    let month = date.getMonth() + 1 + "";
                    // 本月第一天日期
                    start = new Date(year, date.getMonth(), 1);
                    // 本月最后一天日期
                    end = new Date(year, month, 0);
                } else if (type === 6) {
                    let year = date.getFullYear();
                    let month = date.getMonth();
                    if (month == 0) {
                        month = 12;
                        year = year - 1;
                    }
                    // 上月第一天日期
                    start = new Date(year, date.getMonth() - 1, 1);
                    // 上月最后一天日期
                    end = new Date(year, month, 0);
                }
                this.startTime = this.moment(start);
                this.endTime = this.moment(end);
                this.$emit("on-change", [this.moment(start).format("YYYY-MM-DD"), this.moment(end).format("YYYY-MM-DD")], true);
            }
        }
    };
</script>
