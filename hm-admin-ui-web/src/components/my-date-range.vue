<template>
    <div>
        <span class="maintxt mlr10">开始日期:</span>
        <a-date-picker
                :disabled-date="disabledDate"
                @change="changePicker"
                v-model="startTime"
                size="small"
                placeholder="开始日期"
                style="width: 150px"/>
        <span class="maintxt mlr10">结束日期:</span>
        <a-date-picker
                :disabled-date="disabledEnd"
                @change="changePicker"
                v-model="endTime"
                size="small"
                placeholder="结束日期"
                style="width: 150px"/>
    </div>
</template>
<script>
    import Bus from "@/Bus";

    export default {
        data() {
            return {
                startTime: this.todayDate(),
                endTime: this.todayDate(),
            };
        },
        mounted() {
            this.changePicker();
            Bus.$on("upTime", (data) => {
                if(data) {
                    this.startTime = this.moment(data[0]);
                    this.endTime =  this.moment(data[1]);
                }
            });
        },
        methods: {
            disabledDate(current) {
                // return (current && current < this.moment().add(-90, 'day').endOf('day')) || current > this.moment().endOf('day');
            },
            disabledEnd(current) {
                return (current && current < this.moment(this.startTime).add(-1, 'day').endOf('day')) || current > this.moment().endOf('day');
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
                }
                this.startTime = this.moment(start);
                this.endTime = this.moment(end);
                this.$emit("on-change", [this.moment(start).format("YYYY-MM-DD"), this.moment(end).format("YYYY-MM-DD")], true);
            }
        }
    };
</script>
