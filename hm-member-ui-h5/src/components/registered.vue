<template>
  <div>
    <div class="bYNgwR">
      <a class="back-toggle" href="/"></a>
      <div class="title">
        注册账号
      </div>
    </div>
    <form id="form1">
      <div class="field-reminder">
        欢迎来到XX彩票，我们更专注彩票，博彩乐趣都在这里。
      </div>
      <div class="fieldset ">
        <label class="field-label">登录账号</label>
        <input class="field-input"  type="text" placeholder="帐号名由4-10个字符组成" value=""  maxlength="15">
      </div>
      <div class="fieldset ">
        <label class="field-label">登录密码</label>
        <input class="field-input" type="password" placeholder="6-20个字母，数字或英文字母组成，区分大小写" value="">
      </div>
      <div class="fieldset ">
        <label class="field-label">确认密码</label>
        <input class="field-input" name="passwdse" type="password" placeholder="请再次输入密码以确保输入无误" value="">
      </div>
      <div class="fieldset ">
        <label class="field-label">QQ号码</label>
        <input class="field-input" type="text"  placeholder="请常用输入QQ号码" value="">
      </div>
      <div class="fieldset ">
        <label class="field-label">推荐人ID</label>
        <input class="field-input" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入推荐人邀请码" value="">
      </div>

      <div class="fieldset button">
        <button class="field-button" type="submit">创建账号</button>
      </div>
    </form>
  </div>
</template>

<script>
  export default {}
</script>

