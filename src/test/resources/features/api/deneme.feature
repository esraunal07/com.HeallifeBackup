Feature: US_02 Bir yönetici olarak API baglantisi üzerinden Staff List'e erisebilmeliyim

  Scenario: Bir yönetici olarak API baglantisi üzerinden Staff List'e erisebilmeliyim

    Given Api kullanicisi "api/staffList" path parametreleri set eder
    Then Api kullanicisi "staffList" icin gonderdigi dogru get Request sonucunda donen status kodunun 200 oldugunu dogrular
  @wip
  Scenario: /api/staffList endpoint'ine gecersiz authorization bilgileri ile bir GET Request gönderildiginde dönen status code'un 403 oldugu ve response message bilgisinin "failed" oldugu dogrulanmali

    Given Api kullanicisi "api/staffList" path parametreleri set eder
    Then Api kullanicisi "staffList" icin gonderdigi yanlis get Request sonucunda donen status kodunun 403 oldugunu dogrular
