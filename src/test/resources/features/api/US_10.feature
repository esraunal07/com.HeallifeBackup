Feature: Bir yönetici olarak API baglantisi üzerinden id'si verilen kullanicinin Visitor bilgilerine erisebilmeliyim.

  Scenario: TC_01 /api/visitorsId endpoint'ine gecerli authorization bilgileri ve dogru data (id) iceren bir GET body gönderildiginde dönen status code'in 200 oldugu ve response body'deki message bilgisinin "Success" oldugu dogrulanmali
    Given Api kullanicisi {string} icin gonderdigi dogru get Request sonucunda donen status kodunun {200} oldugunu dogrular