import requests
import os
import json
import time
import hmac
import hashlib
import base64
import sys
import urllib.parse
# DingTalk config
dingTalkUrl = "https://oapi.dingtalk.com/robot/send?access_token=xxx"
dingTalkSecret = "xxx"


def getDingTalkUrl(url, secret):
  timestamp = str(round(time.time() * 1000))
  secret_enc = secret.encode('utf-8')
  string_to_sign = '{}\n{}'.format(timestamp, secret)
  string_to_sign_enc = string_to_sign.encode('utf-8')
  hmac_code = hmac.new(secret_enc, string_to_sign_enc,
                       digestmod=hashlib.sha256).digest()
  sign = urllib.parse.quote_plus(base64.b64encode(hmac_code))
  return url + "&timestamp=" + timestamp + "&sign=" + sign


def sendDingMsg(url, msg):
  headers = {"Content-Type": "application/json"}
  sendMsg = {"content": msg}
  sendBody = {"msgtype": "text", "text": sendMsg,"at":{"isAtAll":"false"}}
  res = requests.post(url=url, data=json.dumps(sendBody), headers=headers)
  print("#Send DingTalk robot res:" + str(res.text))

def getVersionInfo():
  cur = os.path.abspath(__file__)
  filePath = os.path.abspath(os.path.join(cur, "../../../versioncontroller/VersionController/src/main/java/com/basestonedata/plugin/versioncontroller/BuildConfig.kt"))
  with open(filePath) as f:
    for line in f.readlines():##readlines(),函数把所有的行都读取进来；
      content = line.strip()##删除行后的换行符，content 就是每行的内容啦
      if(content.startswith("val VERSION_NAME")):
        return content.split('=')[1]

if __name__ == "__main__":
  #具体发送的内容
  branch=sys.argv[1]
  env=sys.argv[2]
  downloadUrl=sys.argv[3]
  qrCodeUrl=sys.argv[4]
  buildNum=sys.argv[5]
  buildType="开发"
  if("beta" == env):
    buildType="测试"
  if("release" == env):
    buildType="生产"
  if("uat" == env):
    buildType="UAT"
  versionCode=getVersionInfo()
  msg="*******Android "+buildType+"环境包*******\n构建编号："+buildNum+"\n"+ "版本信息："+versionCode +"\n"+"分支信息："+branch+"\n"+"apk下载地址:"+downloadUrl+"\n"+"二维码地址："+qrCodeUrl+"\n"
  url = getDingTalkUrl(dingTalkUrl, dingTalkSecret)
  sendDingMsg(url, msg)
