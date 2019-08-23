from  bs4 import  BeautifulSoup as soup
import requests as r
from datetime import datetime

startTime = datetime.now()

url = r.get("https://minneapolis.craigslist.org/d/accounting-finance/search/acc").text
page = soup(url, 'lxml')


for job_title in page.find_all('p', class_='result-info'):
    all_titles = job_title.find('a')
    print(all_titles.text)


print("----------------------------------\n")
print("The script took : "+ str(datetime.now() - startTime) + " to complete execution.")

