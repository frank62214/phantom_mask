# Response
> 此專案分兩個主要的Controller，分別是基本功能、一個是藥局查詢功能。
> 基本功能中，包含交易明細的查詢、交易功能、搜尋功能。
> 藥局查詢功能中，包含藥局的營業時間查詢、藥局販賣的口罩查詢。
> 
> 該服務目前架設於 api.hackdog.tw 中，詳情可點選連結 [Hackdog](https://api.hackdog.tw/swagger-ui/index.html)
> 
> Domain 於 Godaddy購買，並建立於cloudflare上，並透過固定IP將服務轉至個人伺服器中。
> 個人伺服器為Windows主機，透過指令轉導，並於VMware上使用Ubuntu的系統。
> 該虛擬機還有建構Web的功能，故後端服務會先使用Nginx轉導至Docker容器中，
> 服務透過Ubuntu的 Docker Hub，使用建立image，再sh指令進行部屬。
> Database也使用Docker建立postgreSQL的資料庫。
> 
> 最後，詳細的commit紀錄可參閱我個人的 [repo](https://github.com/frank62214/PhantomMask/tree/main)
## A. 服務使用技術
* Windows中進行轉導的指令：netsh interface portproxy add v4tov4。
* Nginx進行轉導，conf可查閱檔案(api.conf)
* Docker進行服務架設
* Springboot為服務框架
* 基礎語言的執行環境為JAVA 17
* 專案使用ORM技術進行資料庫管理
* 資料庫為PostgreSQL
## B. API功能要求
- [X] 取得藥局的開門時間
  - 實做的uri為 queryOpeningHour API.
- [x] 取得藥局所販賣的口罩
  - 實做的uri為 queryMask API.
- [x] 取得價格區間的口罩.
  - 實做的uri為 queryMaskByPrice API.
- [x] 初始化資料表.
  - 實做的uri為 initData API.
- [x] 取得時間區間內，相對應數量的使用者，由金額高到低排序
  - 實做的uri為 queryHighTransactionAmountUser API.
- [x] 取得時間區間內口罩的交易數量
  - 實做的uri為 queryPurchaseMaskByDate API.
- [x] 搜尋功能
  - 實做的uri為 search API.

### A.2. API文件內容
> 可以使用詳情請參閱 [Hackdog-swagger-ui](https://api.hackdog.tw/swagger-ui/index.html)
> 
> 或者將 PhantomMask.postman_collection.json import 到 Postman使用

### A.3. 進行資轉內容
直接使用initDate這個uri即可刷新資料

## B. 加分項目
### B.1. 單元測試
撰寫了主要交易功能的UnitTest

### B.2. 容器化
> 可以參閱Repo裡面的Dockerfile

```bash
docker build -t phantom_mask:1.0 .
docker run -d --restart always --name phantom_mask -p 8082:8082 phantom_mask:1.0
```

### B.3. 進行部屬
URI建立在個人VMware上 [Hackdog](https://api.hackdog.tw/swagger-ui/index.html); you can try any APIs on this demo site.
