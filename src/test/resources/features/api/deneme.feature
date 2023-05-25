Feature: US_02 Bir yönetici olarak API baglantisi üzerinden Staff List'e erisebilmeliyim

  Scenario: Bir yönetici olarak API baglantisi üzerinden Staff List'e erisebilmeliyim

    Given Api kullanicisi "api/staffList" path parametreleri set eder
    Then Api kullanicisi "staffList" icin gonderdigi dogru get Request sonucunda donen status kodunun 200 oldugunu dogrular

  Scenario: /api/staffList endpoint'ine gecersiz authorization bilgileri ile bir GET Request gönderildiginde dönen status code'un 403 oldugu ve response message bilgisinin "failed" oldugu dogrulanmali

    Given Api kullanicisi "api/staffList" path parametreleri set eder
    Then Api kullanicisi "staffList" icin gonderdigi yanlis get Request sonucunda donen status kodunun 403 oldugunu dogrular
  @wip
  Scenario Outline: response body icerisinde istenenlist icerikleri dogrulanmali

    Given Api kullanicisi "api/staffList" path parametreleri set eder
    Then Response body icindeki list icerigi "<id>" olan kisinin isminin "<name>" soyisminin  "<surname>" employe id'sinin "<employee_id>" oldugunu dogrular

    Examples:
      |id | name    | surname  | employee_id |
      |4  | Sansa   | Gomez    | 9008        |
      |10 | Natasha | Romanoff | 9010        |
      |16 | April   | Clinton  | 9020        |
