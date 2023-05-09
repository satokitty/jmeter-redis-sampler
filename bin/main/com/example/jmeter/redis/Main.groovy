package com.github.satokitty.jmeter.redis

import redis.clients.jedis.JedisPooled

def redisHost = vars.get("redis.host")
def redisPort = vars.get("redis.port")

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

println("removed keys: $removedKeys")
println("added keys: $addedKeys")
println("changed:")
changed.each { println("$it.key: expected=${it.value[0]}, actual=${it.value[1]}") }

