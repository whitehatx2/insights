#!/usr/bin/python

import time
import pycurl
import urllib
import json
import oauth2 as oauth

API_ENDPOINT_URL = 'https://stream.twitter.com/1.1/statuses/filter.json'
USER_AGENT = 'TwitterStream 1.0'

OAUTH_KEYS = {'consumer_key': '2zpVcT9pzdG9bXQKGCofA',
              'consumer_secret': 'eIGCq4m3BS2N8e215WWAcFiCBPkGTpZSp4QACOXEs',
              'access_token_key': '116488022-n8id9eVybdHDheyrG7vKi7JRq06gu90kATkn9SHT',
              'access_token_secret': 'BzD0jAvRBlgAAxnPrhbOz5VTMFQ1E1n7sSHQAFw6vLs'}

POST_PARAMS = {'include_entities': 0,
               'stall_warning': 'true',
               'track': 'Huawei,  Ascend P2, Ascend W1, Ascend mate, Ascend D2, Ascend G600, M886, U8651T, M920, U8652, Ascend Y200, Ascend P1, Ascend G300, Ascend P1s, Ascend D1 quad, Ascend X, M865, M835, IDEOS X3, IDEOS X5, M860, IDEOS, U8300, U8110, U8100, U8220, Premia 4G, Ascend Y, Unite Q, Ascend Q, Activa 4G, Ascend II, U8652, U8800, M650, Mediapad, E368, F256, EC5072, E366, E587,  F253, E397Bu-502, FT2260,ZTE, Cricket Engage LT, V768, Z431, WF720, Z331, Z221, AC3781, Rocket 3.0, Rocket 4G,Beats by dre, Beats audio, powerbeats, solo HD,Skullcandy, Hesh 2, Ink.d 2,Bose, QuietComfort, SIE2i, SIE2i, MIE2i, MIE2, AE2, AE2i, OE2, OE2i'}
myFile = open('tweets.txt','w')
class TwitterStream:
    def __init__(self, timeout=False):
        self.oauth_token = oauth.Token(key=OAUTH_KEYS['access_token_key'], secret=OAUTH_KEYS['access_token_secret'])
        self.oauth_consumer = oauth.Consumer(key=OAUTH_KEYS['consumer_key'], secret=OAUTH_KEYS['consumer_secret'])
        self.conn = None
        self.buffer = ''
        self.timeout = timeout
        self.setup_connection()

    def setup_connection(self):
        if self.conn:
            self.conn.close()
            self.buffer = ''
        self.conn = pycurl.Curl()
        if isinstance(self.timeout, int):
            self.conn.setopt(pycurl.LOW_SPEED_LIMIT, 1)
            self.conn.setopt(pycurl.LOW_SPEED_TIME, self.timeout)
        self.conn.setopt(pycurl.URL, API_ENDPOINT_URL)
        self.conn.setopt(pycurl.USERAGENT, USER_AGENT)
        # Using gzip is optional but saves us bandwidth.
        self.conn.setopt(pycurl.ENCODING, 'deflate, gzip')
        self.conn.setopt(pycurl.POST, 1)
        self.conn.setopt(pycurl.POSTFIELDS, urllib.urlencode(POST_PARAMS))
        self.conn.setopt(pycurl.HTTPHEADER, ['Host: stream.twitter.com',
                                             'Authorization: %s' % self.get_oauth_header()])
        self.conn.setopt(pycurl.WRITEFUNCTION, self.handle_tweet)

    def get_oauth_header(self):
        params = {'oauth_version': '1.0',
                  'oauth_nonce': oauth.generate_nonce(),
                  'oauth_timestamp': int(time.time())}
        req = oauth.Request(method='POST', parameters=params, url='%s?%s' % (API_ENDPOINT_URL,
                                                                             urllib.urlencode(POST_PARAMS)))
        req.sign_request(oauth.SignatureMethod_HMAC_SHA1(), self.oauth_consumer, self.oauth_token)
        return req.to_header()['Authorization'].encode('utf-8')

    def start(self):
        """ Start listening to Streaming endpoint.
        Handle exceptions according to Twitter's recommendations.
        """
        backoff_network_error = 0.25
        backoff_http_error = 5
        backoff_rate_limit = 60
        while True:
            self.setup_connection()
            try:
                self.conn.perform()
            except:
                # Network error, use linear back off up to 16 seconds
                print 'Network error: %s' % self.conn.errstr()
                print 'Waiting %s seconds before trying again' % backoff_network_error
                time.sleep(backoff_network_error)
                backoff_network_error = min(backoff_network_error + 1, 16)
                continue
            # HTTP Error
            sc = self.conn.getinfo(pycurl.HTTP_CODE)
            if sc == 420:
                # Rate limit, use exponential back off starting with 1 minute and double each attempt
                print 'Rate limit, waiting %s seconds' % backoff_rate_limit
                time.sleep(backoff_rate_limit)
                backoff_rate_limit *= 2
            else:
                # HTTP error, use exponential back off up to 320 seconds
                print 'HTTP error %s, %s' % (sc, self.conn.errstr())
                print 'Waiting %s seconds' % backoff_http_error
                time.sleep(backoff_http_error)
                backoff_http_error = min(backoff_http_error * 2, 320)

    def handle_tweet(self, data):
        self.buffer += data
        if data.endswith('\r\n') and self.buffer.strip():
            # complete message received
            message = json.loads(self.buffer)
            self.buffer = ''
            msg = ''
            if message.get('limit'):
                print 'Rate limiting caused us to miss %s tweets' % (message['limit'].get('track'))
            elif message.get('disconnect'):
                raise Exception('Got disconnect: %s' % message['disconnect'].get('reason'))
            elif message.get('warning'):
                print 'Got warning: %s' % message['warning'].get('message')
            else:
                print 'Got tweet with text: %s' % message.get('text')
		#myFile = open('tweets.txt', 'w')
		myFile.write(json.dumps(message))
		myFile.write('\n')
		myFile.write('NEW TWEET')



if __name__ == '__main__':
    ts = TwitterStream()
    ts.setup_connection()
    ts.start()
myFile.close()
