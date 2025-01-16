import xeger
import sys
regex = sys.argv[1]
# 使用 xeger 根据正则表达式生成字符串
generated_string = xeger.xeger(regex)
print(generated_string)