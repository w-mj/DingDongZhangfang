package com.dingdonginc.zhangfang.models

class GetFakeData {
     val test: FakeData ?= null

     fun getData(){
         val date = test!!.RandomDate()
     }
}