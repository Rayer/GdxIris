##EventManager設計以及實作

###名詞縮寫以及解釋
- Action Object(AO)
	- Iris內部物件的最小單位
	- 目前設計是可收可發
- Event Prototype(EH)
	- 描述Event內部傳遞的所有訊息以及收發的方法
	- 可以視為是Event的spec
- Event Handler(Handler, EH)
	- 收受Event的單位，通常在AO裡面以一個Field形式存在
	- 也支援特定AO(extend only, 似乎無法做到implement)

###Use case
1. 一個AO註冊「想要聽的Event事件類別」
2. 一個AO以一個Event事件類別對EventManager發送
3. 一個AO能收到註冊過的並且以EH去處理

###Classes
- EventManager
- EventPrototype

###Sync'ed or Async'ed
這是一個設計上的選擇，兩者都會實驗看看。

#####Sync'ed
每次東西一旦進來，就會以Handler去update AO的狀態(很標準的onDemend設計，也就是類似onEvent(Event..)的設計方法)。這方法比較傳統，但是一旦AO導入Threading運算，這可能會產生一些問題。

不過POC會以這個為前提來製作

#####Async'ed
當每次東西進來，就會丟到一個Queue(Java來講的話應該會以LinkedList或者BlockingQueue來實作)。每次AO Update的時候才會從裡面去撈東西出來(可能全撈，也可能一次撈固定的量，也可能一次撈固定的effort value總和)。

這做法比較複雜，但是Threading的時候會比較吃香一點。