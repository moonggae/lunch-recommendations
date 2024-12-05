package dev.egchoi.service

import dev.egchoi.schema.Menu
import dev.egchoi.schema.MenuCategories
import dev.egchoi.schema.MenuCategory
import dev.egchoi.schema.Menus
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class MenuService(private val database: Database) {
    init {
        initializeDatabase()
    }

    private fun initializeDatabase() {
        transaction(database) {
            // 스키마 생성
            SchemaUtils.create(MenuCategories, Menus)

            // 초기 데이터가 없는 경우에만 삽입
            if (MenuCategory.all().count() == 0L) {
                // 카테고리 생성
                val korean = MenuCategory.new {
                    name = "한식"
                }
                val western = MenuCategory.new {
                    name = "양식"
                }
                val chinese = MenuCategory.new {
                    name = "중식"
                }
                val japanese = MenuCategory.new {
                    name = "일식"
                }
                val asian = MenuCategory.new {
                    name = "아시안"
                }

                // 한식 메뉴
                listOf(
                    Triple("비빔밥", "신선한 나물과 고기가 어우러진 영양만점 비빔밥", 1),
                    Triple("김치찌개", "구수한 김치로 끓인 얼큰한 찌개", 2),
                    Triple("된장찌개", "구수한 된장으로 끓인 건강식", 1),
                    Triple("제육볶음", "매콤달콤한 돼지고기 요리", 2),
                    Triple("삼겹살", "푸짐한 삼겹살과 쌈채소", 0),
                    Triple("순두부찌개", "부드러운 순두부로 끓인 얼큰한 찌개", 2),
                    Triple("돼지국밥", "뜨끈한 국물이 일품인 돼지국밥", 1),
                    Triple("닭갈비", "매콤달콤한 닭갈비", 2),
                    Triple("부대찌개", "다양한 재료가 들어간 얼큰한 찌개", 2),
                    Triple("칼국수", "시원한 국물의 칼국수", 0),
                    Triple("갈비찜", "부드럽게 조려진 갈비찜", 1),
                    Triple("떡볶이", "매콤달콤한 추억의 떡볶이", 2),
                    Triple("감자탕", "뼈다귀로 우려낸 구수한 국물", 1),
                    Triple("동태찌개", "얼큰한 동태찌개", 2),
                    Triple("낙곱새", "낙지, 곱창, 새우의 환상 조합", 3),
                    Triple("육개장", "얼큰하고 개운한 육개장", 2),
                    Triple("닭볶음탕", "매콤달콤한 닭볶음탕", 2),
                    Triple("순대국", "담백한 순대국", 0),
                    Triple("곱창구이", "쫄깃한 곱창구이", 1),
                    Triple("보쌈", "부드러운 보쌈과 쌈채소", 0),
                    Triple("쭈꾸미볶음", "매콤한 쭈꾸미볶음", 3),
                    Triple("청국장", "구수한 청국장", 0),
                    Triple("매운갈비찜", "화끈하게 매운 갈비찜", 3),
                    Triple("김치볶음밥", "고소한 김치볶음밥", 1),
                    Triple("해물파전", "바삭한 해물파전", 0),
                    Triple("낙지볶음", "매콤한 낙지볶음", 3),
                    Triple("콩나물국밥", "시원한 콩나물국밥", 0),
                    Triple("미역국", "담백한 미역국", 0),
                    Triple("설렁탕", "진한 국물의 설렁탕", 0),
                    Triple("갈비탕", "진한 갈비육수의 갈비탕", 0),
                    Triple("참치김밥", "고소한 참치와 채소가 어우러진 클래식 김밥", 0),
                    Triple("불고기김밥", "달콤한 불고기가 들어간 영양만점 김밥", 0),
                    Triple("치즈김밥", "고소한 치즈가 들어간 담백한 김밥", 0),
                    Triple("김치김밥", "매콤한 김치로 속을 채운 김밥", 1),
                    Triple("멸치김밥", "잔멸치 볶음이 들어간 고소한 김밥", 0),
                    Triple("누드김밥", "김 없이 속만 돌돌 말아낸 김밥", 0),
                    Triple("소고기김밥", "프리미엄 소고기로 만든 특별한 김밥", 0),
                    Triple("계란말이김밥", "계란말이가 들어간 담백한 김밥", 0),
                    Triple("제육김밥", "매콤한 제육볶음이 들어간 김밥", 2),
                    Triple("새우김밥", "탱글탱글한 새우가 들어간 김밥", 0),
                    Triple("야채김밥", "신선한 야채로만 가득 채운 김밥", 0),
                    Triple("깻잎김밥", "향긋한 깻잎이 들어간 김밥", 0),
                    Triple("스팸김밥", "스팸과 채소가 조화로운 김밥", 0),
                    Triple("돈까스김밥", "바삭한 돈까스가 들어간 김밥", 0),
                    Triple("청양고추김밥", "알싸한 청양고추가 들어간 매운 김밥", 3),
                    Triple("매운어묵김밥", "매콤한 어묵볶음이 들어간 김밥", 2),
                    Triple("묵은지참치김밥", "", 2),
                    Triple("뚝배기불고기", "", 2),
                ).forEach {
                    Menu.new {
                        name = it.first
                        category = korean
                        description = it.second
                        spicyLevel = it.third
                    }
                }

                // 양식 메뉴
                listOf(
                    Triple("파스타", "알덴테로 삶은 파스타", 0),
                    Triple("피자", "토핑이 풍성한 피자", 0),
                    Triple("함박스테이크", "육즙 가득한 함박스테이크", 0),
                    Triple("리조또", "크리미한 리조또", 0),
                    Triple("샐러드", "신선한 채소로 만든 샐러드", 0),
                    Triple("햄버거", "두툼한 패티의 햄버거", 0),
                    Triple("스테이크", "풍미 가득한 스테이크", 0),
                    Triple("크림파스타", "부드러운 크림 소스 파스타", 0),
                    Triple("로제파스타", "크림과 토마토의 조화", 0),
                    Triple("치킨샐러드", "닭가슴살이 들어간 샐러드", 0),
                    Triple("시저샐러드", "진한 시저드레싱의 샐러드", 0),
                    Triple("클럽샌드위치", "풍성한 具가 들어간 샌드위치", 0),
                    Triple("폭립", "부드러운 폭립", 1),
                    Triple("빠네파스타", "빵그릇에 담긴 파스타", 0),
                    Triple("그라탕", "치즈가 녹아내리는 그라탕", 0),
                    Triple("포크커틀릿", "바삭한 돼지고기 커틀릿", 0),
                    Triple("비프스튜", "진한 육수의 비프스튜", 0),
                    Triple("감바스", "마늘향 가득한 새우요리", 1),
                    Triple("치킨까르보나라", "닭고기가 들어간 까르보나라", 0),
                    Triple("쉬림프로제파스타", "새우가 들어간 로제파스타", 0),
                    Triple("스테이크샐러드", "스테이크가 올라간 샐러드", 0)
                ).forEach {
                    Menu.new {
                        name = it.first
                        category = western
                        description = it.second
                        spicyLevel = it.third
                    }
                }

                // 중식 메뉴
                listOf(
                    Triple("짜장면", "구수한 춘장으로 만든 짜장면", 0),
                    Triple("짬뽕", "얼큰한 국물의 짬뽕", 2),
                    Triple("마라탕", "얼얼한 마라탕", 3),
                    Triple("탕수육", "바삭한 탕수육", 0),
                    Triple("양장피", "신선한 해산물이 들어간 양장피", 0),
                    Triple("깐풍기", "매콤달콤한 깐풍기", 1),
                    Triple("마파두부", "매콤한 마파두부", 2),
                    Triple("고추잡채", "매콤한 고추잡채", 1),
                    Triple("유린기", "바삭한 닭고기 요리", 0),
                    Triple("동파육", "부드러운 동파육", 0),
                    Triple("마라샹궈", "얼얼한 마라향의 볶음요리", 3),
                    Triple("깐쇼새우", "매콤달콤한 깐쇼새우", 2),
                    Triple("마늘소스새우", "마늘향 가득한 새우요리", 0),
                    Triple("양꼬치", "쫄깃한 양고기 구이", 1),
                    Triple("해물누룽지탕", "바삭한 누룽지의 해물탕", 0),
                    Triple("고추자장면", "매콤한 자장면", 1),
                    Triple("삼선짬뽕", "해물이 풍성한 짬뽕", 2),
                    Triple("라조기", "매콤한 닭고기 요리", 2),
                    Triple("꿔바로우", "바삭하고 달콤한 탕수육", 0),
                    Triple("마파밥", "매콤한 마파소스 덮밥", 2),
                    Triple("유산슬", "해물과 채소의 볶음요리", 0),
                    Triple("팔보채", "8가지 재료의 볶음요리", 0)
                ).forEach {
                    Menu.new {
                        name = it.first
                        category = chinese
                        description = it.second
                        spicyLevel = it.third
                    }
                }

                // 일식 메뉴
                listOf(
                    Triple("초밥", "신선한 회로 만든 초밥", 0),
                    Triple("라멘", "진한 국물의 라멘", 1),
                    Triple("돈카츠", "바삭한 돈카츠", 0),
                    Triple("우동", "쫄깃한 면발의 우동", 0),
                    Triple("규동", "부드러운 소고기 덮밥", 0),
                    Triple("가라아게", "바삭한 닭튀김", 0),
                    Triple("오므라이스", "폭신한 달걀 오믈렛", 0),
                    Triple("규카츠", "바삭한 소고기 커틀릿", 0),
                    Triple("사케동", "신선한 연어 덮밥", 0),
                    Triple("가츠동", "돈카츠 덮밥", 0),
                    Triple("규카레", "부드러운 소고기 카레", 1),
                    Triple("텐동", "바삭한 튀김 덮밥", 0),
                    Triple("차슈라멘", "챠슈가 들어간 라멘", 1),
                    Triple("미소라멘", "구수한 미소라멘", 0),
                    Triple("돈코츠라멘", "진한 돈코츠라멘", 1),
                    Triple("스키야키", "달콤한 전골요리", 0),
                    Triple("샤브샤브", "깔끔한 전골요리", 0),
                    Triple("규니쿠덮밥", "매콤한 소고기 덮밥", 2),
                    Triple("돈부리", "다양한 토핑의 덮밥", 0),
                    Triple("오니기리", "삼각김밥", 0),
                    Triple("타코야키", "문어가 들어간 간식", 0),
                    Triple("나베", "일본식 전골", 0)
                ).forEach {
                    Menu.new {
                        name = it.first
                        category = japanese
                        description = it.second
                        spicyLevel = it.third
                    }
                }

                // 아시안 메뉴
                listOf(
                    Triple("쌀국수", "베트남 쌀국수", 1),
                    Triple("팟타이", "태국식 볶음면", 1),
                    Triple("나시고랭", "인도네시아식 볶음밥", 1),
                    Triple("분짜", "베트남식 구운 돼지고기와 쌀국수", 0),
                    Triple("똠양꿍", "태국식 새우 수프", 2),
                    Triple("카오팟", "태국식 볶음밥", 1)
                ).forEach {
                    Menu.new {
                        name = it.first
                        category = asian
                        description = it.second
                        spicyLevel = it.third
                    }
                }
            }
        }
    }

    fun getRandomMenu(categoryName: String? = null):Menu {
        return transaction(database) {
            val query = if (categoryName != null) {
                Menu.find { Menus.category eq MenuCategory.find { MenuCategories.name eq categoryName }.first().id }
            } else {
                Menu.all()
            }
            query.toList().random()
        }
    }
}
