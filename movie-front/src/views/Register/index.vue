<script setup>
import { reactive, ref } from 'vue'
import { ElInput } from 'element-plus'
import { useUserStore } from '@/stores/userStore'
import router from '@/router'
import { postUserRegisterAPI } from "@/apis/user"

// 表单对象
const ruleFormRef = ref()

// 登录表单数据
const registerDTO = reactive({
    mail: '',
    name: '',
    password: '',
    password2: '',
})

// 检查手机号格式
const checkMail = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请输入邮箱'))
    }
    setTimeout(() => {
        if (!String(value).match(/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/)) {
            callback(new Error('请输入正确的邮箱'))
        } else {
            callback()
        }
    }, 1000)
}

// 检测两次密码一致
const checkPwd2 = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('请再次输入密码'))
    }
    setTimeout(() => {
        if (value != registerDTO.password) {
            callback(new Error('两次密码不一致'))
        } else {
            callback()
        }
    }, 1000)
}

// 规则数据对象
const rules = reactive({
    mail: [
        { validator: checkMail, trigger: 'blur' }
    ],
    password: [
        { required: true, message: '密码不能为空', trigger: 'blur' },
        { min: 6, max: 24, message: '密码长度要求6-14个字符', trigger: 'blur' }
    ],
    password2: [
        { validator: checkPwd2, trigger: 'blur' }
    ]
})

// 提交表单
const submitForm = (formEl) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            onRegister(registerDTO)
        } else {
            return false
        }
    })
}


// 用户登录
const onRegister = async (registerDTO) => {
    const res = await postUserRegisterAPI(registerDTO)
    // 持久化保存用户信息
    useUserStore().saveUserInfo(res.data)
    // 跳转到首页
    router.replace({ path: '/login' })
}


</script>


<template>
    <div class="main">

        <div class="board">

            <div class="title">
                天堂<span class="strong">电影</span>
            </div>

            <el-form class="form"
                     ref="ruleFormRef"
                     :model="registerDTO"
                     :rules="rules">
                <el-form-item prop="mail">
                    <el-input v-model="registerDTO.mail"
                              placeholder="请输入邮箱">
                        <template #prefix>
                            <el-icon class="el-input__icon">
                                <Message />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="name">
                    <el-input v-model="registerDTO.name"
                              placeholder="请输入用户名">
                        <template #prefix>
                            <el-icon class="el-input__icon">
                                <User />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="registerDTO.password"
                              type="password"
                              placeholder="请输入密码">
                        <template #prefix>
                            <el-icon class="el-input__icon">
                                <Lock />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password2">
                    <el-input v-model="registerDTO.password2"
                              type="password"
                              placeholder="请确认密码">
                        <template #prefix>
                            <el-icon class="el-input__icon">
                                <Lock />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"
                               style="width: 100%; margin: 1rem auto;"
                               @click="submitForm(ruleFormRef)">注册</el-button>
                </el-form-item>
            </el-form>

        </div>

    </div>
</template>


<style scoped lang="scss">
.main {
    width: 100%;
    height: 100%;
}

.board {
    // line-height: 95%;
    height: 95%;
    margin: 5rem;
}

.form {
    max-width: 300px;
    margin: 2rem auto;
}

.title {
    color: white;
    font-size: 50px;
    font-weight: bold;
    text-align: center;
    margin: auto 0;
    letter-spacing: 3px;
    cursor: pointer;
    text-wrap: nowrap;
    line-height: 140px;

    .strong {
        background-color: $mainColor;
        padding-left: 3px;
        border-radius: 5px;
        margin-right: 3px;
        color: #111;
    }
}
</style>