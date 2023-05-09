import redis.clients.jedis.JedisPooled

def redisHost = vars.get("redis.host")
def redisPort = vars.get("redis.port").toInteger()

def jedis = new JedisPooled(redisHost, redisPort)

def prepareData = [
  "key1": "value1",
  "key2": "value3"
]

jedis.hset("testHash", prepareData)
