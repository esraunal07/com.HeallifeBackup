Feature: US_01 Bir yönetici olarak API baglantisi üzerinden OPD List'e erisebilmeliyim.

  Scenario: TC_01 api/opdList endpoint'ine gecerli authorization bilgileri ile bir GET request gönderildiginde
  dönen status code'un 200 oldugu ve response message bilgisinin "Success" oldugu dogrulanmali

    Given Api kullanicisi "api/opdList" path parametreleri set eder
    Then  Api kullanicisi "api/opdList" icin gonderdigi dogru get Request sonucunda donen status kodunun 200 oldugunu dogrular


  Scenario: TC_02 api/opdList endpoint'ine gecersiz authorization bilgileri ile bir GET Request gönderildiginde
  dönen status code'un 403 oldugu ve response message bilgisinin "failed" oldugu dogrulanmali

    Given Api kullanicisi "api/opdList" path parametreleri set eder
    Then  Api kullanicisi "api/opdList" icin gonderdigi yanlis get Request sonucunda donen status kodunun 403 oldugunu dogrular

    @esra
  Scenario: TC_03  Response body icindeki lists icerigi
  (id:"1" olan icerigin patient_name: "John Smith" ve id:"23" olan icerigin patient_id: "16" oldugu) dogrulanmali.

    Given Api kullanicisi "api/opdList" path parametreleri set eder
    Then  Api kullanicisi id'si 1 olan kaydin patient_name: "John Smith", expected datasi hazirlanir
    Then  Api kullanicisi Response body List gormek icin Get request gonderir
    Then  Api kullanicisi  donen response body icindeki id'si "1" olan kaydin patient_name: "John Smith"  oldugunu dogrular
    And   Api kullanicisi donen response bodysinin id'si "23" olanin patient_id: "16" oldugu dogrular