import redis.clients.jedis.JedisPooled

def redisHost = vars.get("redis.host")
def redisPort = vars.get("redis.port").toInteger()

def jedis = new JedisPooled(redisHost, redisPort)

def actual = jedis.hgetAll("testHash")

def expected = [
  "key1": "value1",
  "key2": "value2",
]

def expectedKeys = expected.keySet()
def actualKeys = actual.keySet()

def removedKeys = expectedKeys - actualKeys
def addedKeys = actualKeys - expectedKeys
Map<String, List<String>> changed = (actualKeys - addedKeys).findAll { expected[it] != actual[it] }.collectEntries { [it, [expected[it], actual[it]]]}

def resultChanged = changed.collect { "$it.key: expected=${it.value[0]}, actual=${it.value[1]}" }.join("\n")

SampleResult.setResponseData("""removed keys: $removedKeys
added keys: $addedKeys
changed:
$resultChanged
""")
