import os
import requests as rq
from lxml import html
import json

class Head:    
    def __init__(self):
        self.name = ""
        self.b64 = ""
        self.tags = []
        self.ingredients = {}
    
    def from_url(self, url):
        resp = rq.get(url)
        if resp.status_code != 200:
            raise rq.exceptions.RequestException()
        
        tree = html.fromstring(resp.content)
        
        name = tree.xpath('//*[@id="main"]/div/div[2]/div[1]/div/h2')[0].text.strip()
        b64 = tree.xpath('//*[@id="UUID-Value"]')[0].text
        tags = []
        
        for a in tree.xpath('//*[@id="main"]/div/div[2]/div[1]/div/a'):
            if "href" not in a.keys():
                continue
            if a.get("href").startswith("/custom-heads/tags/var/"):
                tags.append(a.text[:-1])
        
        self.name = name
        self.b64 = b64
        self.tags = tags
    
    def add_ingredient(self, mat, amount):
        self.ingredients[mat.upper()] = amount
    
    def to_dict(self):
        return {"result": self.b64, "name": self.name, "tags": self.tags, "ingredients": self.ingredients}
    
    def remove_ingredient(self, mat):
        if mat in self.ingredients.keys():
            self.ingredients.pop(mat)
    
    def to_json(self):
        return json.dumps(self.to_dict(), indent=2)
