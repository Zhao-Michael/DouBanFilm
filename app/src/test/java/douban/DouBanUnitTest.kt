package douban

import com.google.gson.Gson
import junit.framework.Assert.assertNotNull
import org.junit.Test
import util.*

class DouBanUnitTest {

    val strFilmList = get_strFilmList()

    val strFilmDetail = get_strFilmDetail()

    val strFilmPhoto = get_strFilmPhoto()

    val strFilmReview = get_strFilmReview()

    val strFilmComment = get_strFilmComment()

    fun get_strFilmList(): String {
        return "{\n" +
                "    \"count\": 20,\n" +
                "    \"start\": 0,\n" +
                "    \"total\": 38,\n" +
                "    \"subjects\": [\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 10,\n" +
                "                \"average\": 6.5,\n" +
                "                \"stars\": \"35\",\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"genres\": [\n" +
                "                \"\\u5267\\u60c5\",\n" +
                "                \"\\u559c\\u5267\"\n" +
                "            ],\n" +
                "            \"title\": \"\\u7238\\uff0c\\u6211\\u4e00\\u5b9a\\u884c\\u7684\",\n" +
                "            \"casts\": [\n" +
                "                {\n" +
                "                    \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1397572\\/\",\n" +
                "                    \"avatars\": {\n" +
                "                        \"small\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532328085.6.webp\",\n" +
                "                        \"large\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532328085.6.webp\",\n" +
                "                        \"medium\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532328085.6.webp\"\n" +
                "                    },\n" +
                "                    \"name\": \"\\u90d1\\u6da6\\u5947\",\n" +
                "                    \"id\": \"1397572\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1397576\\/\",\n" +
                "                    \"avatars\": {\n" +
                "                        \"small\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1531903755.61.webp\",\n" +
                "                        \"large\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1531903755.61.webp\",\n" +
                "                        \"medium\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1531903755.61.webp\"\n" +
                "                    },\n" +
                "                    \"name\": \"\\u90d1\\u9e4f\\u751f\",\n" +
                "                    \"id\": \"1397576\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1373523\\/\",\n" +
                "                    \"avatars\": {\n" +
                "                        \"small\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532328107.17.webp\",\n" +
                "                        \"large\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532328107.17.webp\",\n" +
                "                        \"medium\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532328107.17.webp\"\n" +
                "                    },\n" +
                "                    \"name\": \"\\u5f20\\u548f\\u5a34\",\n" +
                "                    \"id\": \"1373523\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"collect_count\": 2178,\n" +
                "            \"original_title\": \"\\u7238\\uff0c\\u6211\\u4e00\\u5b9a\\u884c\\u7684\",\n" +
                "            \"subtype\": \"movie\",\n" +
                "            \"directors\": [\n" +
                "                {\n" +
                "                    \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1397571\\/\",\n" +
                "                    \"avatars\": {\n" +
                "                        \"small\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532077330.37.webp\",\n" +
                "                        \"large\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532077330.37.webp\",\n" +
                "                        \"medium\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1532077330.37.webp\"\n" +
                "                    },\n" +
                "                    \"name\": \"\\u84dd\\u9e3f\\u6625\",\n" +
                "                    \"id\": \"1397571\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"year\": \"2018\",\n" +
                "            \"images\": {\n" +
                "                \"small\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2530908793.webp\",\n" +
                "                \"large\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2530908793.webp\",\n" +
                "                \"medium\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2530908793.webp\"\n" +
                "            },\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/subject\\/30276726\\/\",\n" +
                "            \"id\": \"30276726\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"title\": \"\\u6b63\\u5728\\u4e0a\\u6620\\u7684\\u7535\\u5f71-\\u5317\\u4eac\"\n" +
                "}"
    }

    fun get_strFilmDetail(): String {
        return "{\n" +
                "    \"rating\": {\n" +
                "        \"max\": 10,\n" +
                "        \"average\": 2.4,\n" +
                "        \"details\": {\n" +
                "            \"1\": 71.0,\n" +
                "            \"3\": 3.0,\n" +
                "            \"2\": 6.0,\n" +
                "            \"5\": 1.0,\n" +
                "            \"4\": 0.0\n" +
                "        },\n" +
                "        \"stars\": \"15\",\n" +
                "        \"min\": 0\n" +
                "    },\n" +
                "    \"reviews_count\": 13,\n" +
                "    \"videos\": [\n" +
                "        {\n" +
                "            \"source\": {\n" +
                "                \"literal\": \"qq\",\n" +
                "                \"pic\": \"http://img7.doubanio.com\\/f\\/movie\\/0a74f4379607fa731489d7f34daa545df9481fa0\\/pics\\/movie\\/video-qq.png\",\n" +
                "                \"name\": \"\\u817e\\u8baf\\u89c6\\u9891\"\n" +
                "            },\n" +
                "            \"sample_link\": \"http:\\/\\/v.qq.com\\/x\\/cover\\/xzvr5axh7r6u524.html?ptag=douban.movie\",\n" +
                "            \"video_id\": \"xzvr5axh7r6u524\",\n" +
                "            \"need_pay\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"wish_count\": 239,\n" +
                "    \"original_title\": \"\\u6050\\u6016\\u7406\\u53d1\\u5e97\",\n" +
                "    \"blooper_urls\": [],\n" +
                "    \"collect_count\": 707,\n" +
                "    \"images\": {\n" +
                "        \"small\": \"http://img7.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\",\n" +
                "        \"large\": \"http://img7.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\",\n" +
                "        \"medium\": \"http://img7.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\"\n" +
                "    },\n" +
                "    \"douban_site\": \"\",\n" +
                "    \"year\": \"2017\",\n" +
                "    \"popular_comments\": [\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"useful_count\": 0,\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"chrisnmoon\",\n" +
                "                \"avatar\": \"http://img3.doubanio.com\\/icon\\/u2806089-7.jpg\",\n" +
                "                \"signature\": \"\\u82f1\\u56fd\\u864e\\u6591\\uff0c\\u540d\\u53eb\\u4e00\\u6761\\u3002\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/chrisnmoon\\/\",\n" +
                "                \"id\": \"2806089\",\n" +
                "                \"name\": \"\\u53ea\\u7231\\u8fd9\\u7247\\u571f\\u5730\\uff01\"\n" +
                "            },\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"content\": \"\\u88ab\\u8c46\\u74e3\\u5413\\u7740\\u4e86\\uff0c\\u6240\\u4ee5\\u6ca1\\u6709\\u770b\\u3002\\u6ca1\\u8d44\\u683c\\u54c1\\u5934\\u8bba\\u8db3\\uff0c\\u4e0d\\u8fc7\\u770b\\u5bfc\\u6f14\\u4ecb\\u7ecd\\u7684\\u65f6\\u5019\\u53d1\\u73b0\\u4e24\\u70b9\\u201c\\u6050\\u6016\\u201d\\u4e4b\\u5904\\uff1a1. \\u751f\\u4e8e1969-09-07\\uff0c\\u662f\\u53cc\\u5b50\\u5ea7\\u30022.\\u66fe\\u53c2\\u4e0e\\u62cd\\u6444\\u7535\\u5f71\\u300a\\u534a\\u591c\\u53eb\\u4f60\\u522b\\u56de\\u5934\\u300b\\u300a\\u5e8a\\u4e0b\\u6709\\u4eba3\\u300b \\u7b49**\\u4f18\\u79c0**\\u56fd\\u4ea7\\u7535\\u5f71!\",\n" +
                "            \"created_at\": \"2017-10-04 17:29:48\",\n" +
                "            \"id\": \"1251621067\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"alt\": \"https:\\/\\/movie.douban.com\\/subject\\/26865690\\/\",\n" +
                "    \"id\": \"26865690\",\n" +
                "    \"mobile_url\": \"https:\\/\\/movie.douban.com\\/subject\\/26865690\\/mobile\",\n" +
                "    \"photos_count\": 27,\n" +
                "    \"pubdate\": \"2017-01-06\",\n" +
                "    \"title\": \"\\u6050\\u6016\\u7406\\u53d1\\u5e97\",\n" +
                "    \"do_count\": null,\n" +
                "    \"has_video\": true,\n" +
                "    \"share_url\": \"http:\\/\\/m.douban.com\\/movie\\/subject\\/26865690\",\n" +
                "    \"seasons_count\": null,\n" +
                "    \"languages\": [\n" +
                "        \"\\u6c49\\u8bed\\u666e\\u901a\\u8bdd\"\n" +
                "    ],\n" +
                "    \"schedule_url\": \"\",\n" +
                "    \"writers\": [\n" +
                "        {\n" +
                "            \"avatars\": {\n" +
                "                \"small\": \"http://img3.doubanio.com\\/f\\/movie\\/ca527386eb8c4e325611e22dfcb04cc116d6b423\\/pics\\/movie\\/celebrity-default-small.png\",\n" +
                "                \"large\": \"http://img7.doubanio.com\\/f\\/movie\\/63acc16ca6309ef191f0378faf793d1096a3e606\\/pics\\/movie\\/celebrity-default-large.png\",\n" +
                "                \"medium\": \"http://img3.doubanio.com\\/f\\/movie\\/8dd0c794499fe925ae2ae89ee30cd225750457b4\\/pics\\/movie\\/celebrity-default-medium.png\"\n" +
                "            },\n" +
                "            \"name_en\": \"\",\n" +
                "            \"name\": \"\\u7eaa\\u7136\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1366595\\/\",\n" +
                "            \"id\": \"1366595\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatars\": {\n" +
                "                \"small\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\",\n" +
                "                \"large\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\",\n" +
                "                \"medium\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\"\n" +
                "            },\n" +
                "            \"name_en\": \"Shilei Lu\",\n" +
                "            \"name\": \"\\u9646\\u8bd7\\u96f7\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1360707\\/\",\n" +
                "            \"id\": \"1360707\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pubdates\": [\n" +
                "        \"2017-01-06(\\u4e2d\\u56fd\\u5927\\u9646)\"\n" +
                "    ],\n" +
                "    \"website\": \"\",\n" +
                "    \"tags\": [\n" +
                "        \"\\u60ca\\u609a\",\n" +
                "        \"\\u70c2\\u7247\",\n" +
                "        \"\\u4e00\\u4e2a\\u661f\\u90fd\\u4e0d\\u60f3\\u7ed9\\uff01\",\n" +
                "        \"\\u70c2\\u7247\\u4e4b\\u4e2d\\u7684\\u70c2\\u7247\\u554a~\",\n" +
                "        \"\\u5783\\u573e\",\n" +
                "        \"\\u4e2d\\u56fd\",\n" +
                "        \"\\u5475\\u5475\",\n" +
                "        \"\\u72d7\\u5c4e\",\n" +
                "        \"\\u70c2\\u900f\\u4e86\",\n" +
                "        \"\\u771f\\u7684\\u597d\\u6050\\u6016\\u554a\\uff01\"\n" +
                "    ],\n" +
                "    \"has_schedule\": false,\n" +
                "    \"durations\": [\n" +
                "        \"89\\u5206\\u949f\"\n" +
                "    ],\n" +
                "    \"genres\": [\n" +
                "        \"\\u7231\\u60c5\",\n" +
                "        \"\\u60ac\\u7591\",\n" +
                "        \"\\u60ca\\u609a\"\n" +
                "    ],\n" +
                "    \"collection\": null,\n" +
                "    \"trailers\": [\n" +
                "        {\n" +
                "            \"medium\": \"http://img3.doubanio.com\\/img\\/trailer\\/medium\\/2395934439.jpg?\",\n" +
                "            \"title\": \"\\u9884\\u544a\\u7247\\uff1a\\u6b63\\u5f0f\\u7248 (\\u4e2d\\u6587\\u5b57\\u5e55)\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/trailer\\/206905\\/\",\n" +
                "            \"small\": \"http://img3.doubanio.com\\/img\\/trailer\\/small\\/2395934439.jpg?\",\n" +
                "            \"resource_url\": \"http:\\/\\/vt1.doubanio.com\\/201808272224\\/9f5cd2988df492ee52242347576b844e\\/view\\/movie\\/M\\/302060905.mp4\",\n" +
                "            \"id\": \"206905\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"episodes_count\": null,\n" +
                "    \"trailer_urls\": [\n" +
                "        \"http:\\/\\/vt1.doubanio.com\\/201808272224\\/9f5cd2988df492ee52242347576b844e\\/view\\/movie\\/M\\/302060905.mp4\",\n" +
                "        \"http:\\/\\/vt1.doubanio.com\\/201808272224\\/567daeac1ba81195cb6315e63c64b295\\/view\\/movie\\/M\\/302090536.mp4\",\n" +
                "        \"http:\\/\\/vt1.doubanio.com\\/201808272224\\/2fbb19951bee48100df4aa433ea31336\\/view\\/movie\\/M\\/302090076.mp4\"\n" +
                "    ],\n" +
                "    \"has_ticket\": false,\n" +
                "    \"bloopers\": [],\n" +
                "    \"clip_urls\": [],\n" +
                "    \"current_season\": null,\n" +
                "    \"casts\": [\n" +
                "        {\n" +
                "            \"avatars\": {\n" +
                "                \"small\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\",\n" +
                "                \"large\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\",\n" +
                "                \"medium\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\"\n" +
                "            },\n" +
                "            \"name_en\": \"Guoer Yin\",\n" +
                "            \"name\": \"\\u6bb7\\u679c\\u513f\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1340984\\/\",\n" +
                "            \"id\": \"1340984\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"countries\": [\n" +
                "        \"\\u4e2d\\u56fd\\u5927\\u9646\"\n" +
                "    ],\n" +
                "    \"mainland_pubdate\": \"2017-01-06\",\n" +
                "    \"photos\": [\n" +
                "        {\n" +
                "            \"thumb\": \"http://img7.doubanio.com\\/view\\/photo\\/m\\/public\\/p2411789693.webp\",\n" +
                "            \"image\": \"http://img7.doubanio.com\\/view\\/photo\\/l\\/public\\/p2411789693.webp\",\n" +
                "            \"cover\": \"http://img7.doubanio.com\\/view\\/photo\\/sqs\\/public\\/p2411789693.webp\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/photos\\/photo\\/2411789693\\/\",\n" +
                "            \"id\": \"2411789693\",\n" +
                "            \"icon\": \"http://img7.doubanio.com\\/view\\/photo\\/s\\/public\\/p2411789693.webp\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"summary\": \"\\u4f4d\\u4e8e\\u6df1\\u5c71\\u5c0f\\u9547\\u7684\\u7406\\u53d1\\u5e97\\u53d1\\u751f\\u7684\\u4e00\\u7cfb\\u5217\\u7075\\u5f02\\u5947\\u95fb\\uff0c\\u6bb7\\u679c\\u513f\\u3001\\u4efb\\u9752\\u5b89\\u3001\\u59dc\\u661f\\u4e18\\u7b49\\u4eba\\u9677\\u5165\\u5371\\u96be\\u7edd\\u5883\\u4e2d\\u65e0\\u6cd5\\u8131\\u8eab\\uff0c\\u548c\\u7406\\u53d1\\u5e97\\u6709\\u5173\\u8054\\u7684\\u4eba\\u7269\\u63a5\\u8fde\\u88ab\\u60e8\\u7edd\\u6740\\u5bb3\\uff0c\\u8840\\u8165\\u6b8b\\u66b4\\u5f15\\u6765\\u4eba\\u5fc3\\u60f6\\u60f6\\uff0c\\u800c\\u62bd\\u4e1d\\u5265\\u8327\\u4e4b\\u540e\\u7684\\u771f\\u76f8\\u66f4\\u52a0\\u4ee4\\u4eba\\u5fc3\\u60ca\\u80c6\\u6218\\u3002\",\n" +
                "    \"clips\": [],\n" +
                "    \"subtype\": \"movie\",\n" +
                "    \"directors\": [\n" +
                "        {\n" +
                "            \"avatars\": {\n" +
                "                \"small\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\",\n" +
                "                \"large\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\",\n" +
                "                \"medium\": \"http://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\"\n" +
                "            },\n" +
                "            \"name_en\": \"Shilei Lu\",\n" +
                "            \"name\": \"\\u9646\\u8bd7\\u96f7\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1360707\\/\",\n" +
                "            \"id\": \"1360707\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"comments_count\": 245,\n" +
                "    \"popular_reviews\": [\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 1.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\uff0c\\u6ce8\\u5b9a\\u6210\\u70c2\\u7247\\uff1f\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"123404248\",\n" +
                "                \"avatar\": \"http://img7.doubanio.com\\/icon\\/u123404248-5.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/123404248\\/\",\n" +
                "                \"id\": \"123404248\",\n" +
                "                \"name\": \"\\u4e16\\u754c\\u5947\\u5999\\u7269\\u8bed\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u8fd9\\u4e00\\u7cfb\\u5217\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\u592a\\u591a\\uff0c\\u73b0\\u5728\\u603b\\u7ed3\\u4e0b\\u56fd\\u4ea7\\u7535\\u5f71\\u62cd\\u6444\\u95e8\\u69db\\u4e3a\\u4ec0\\u4e48\\u8fd9\\u4e48\\u4f4e\\u2026\\u2026 1.\\u627e\\u4e2a\\u5bfc\\u6f14\\uff0c\\u5185\\u5730\\u5bfc\\u6f14\\u4f18\\u5148\\u8003\\u8651(\\u7701\\u94b1)\\u3002 2.\\u53bb\\u7f51\\u4e0a\\u70ed\\u641c\\u699c\\uff08\\u4e5f\\u662f\\u7ecf\\u7eaa\\u516c\\u53f8\\uff09\\u4e0a\\u6311\\u51e0\\u4e2a\\u7f51\\u7ea2\\u660e\\u661f\\uff08\\u7701\\u94b1\\uff09\\u3002\\u7f51\\u7ea2\\u660e\\u661f\\u5c31\\u50cf\\u6728\\u5076\\u4e00\\u6837\\u88ab\\u88c5\\u626e\\u4e0a\\u4e86\\u3002 3.\\u53bb...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8301338\\/\",\n" +
                "            \"id\": \"8301338\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 1.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"\\u5bfc\\u6f14\\u522b\\u62cd\\u7535\\u5f71\\u4e86\\uff0c\\u5feb\\u56de\\u5bb6\\u966a\\u4f60\\u7236\\u6bcd\\uff0c\\u4e0d\\u7136\\u5c0f\\u5fc3\\u4ed6\\u4eec\\u626e\\u9b3c\\u5413\\u4f60\\uff01\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"BIANJU20170418\",\n" +
                "                \"avatar\": \"http://img7.doubanio.com\\/icon\\/u82851721-3.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/BIANJU20170418\\/\",\n" +
                "                \"id\": \"82851721\",\n" +
                "                \"name\": \"\\u6e38\\u4fa0\\u4e00\\u7b11\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u300a\\u6050\\u6016\\u6e38\\u6cf3\\u9986\\u300b\\u3001\\u300a\\u6050\\u6016\\u7535\\u5f71\\u9662\\u300b\\uff0c\\u6050\\u6016\\u5395\\u6240\\u3001\\u6050\\u6016\\u4f60\\u5988\\u9694\\u58c1\\uff0c\\u7ee7\\u201c\\u8be1\\u201d\\u3001\\u201c\\u60ca\\u9b42\\u201d\\u3001\\u201c\\u7075\\u201d\\u3001\\u201c\\u6028\\u201d\\u540e\\uff0c\\u56fd\\u4ea7\\u53ef\\u6015\\u7247\\u7684\\u7247\\u540d\\u8a93\\u8981\\u5728\\u201c\\u6050\\u6016\\u201d\\u8def\\u4e0a\\u8d70\\u5230\\u5e95\\u3002  \\u4e00\\u8fde\\u770b\\u4e86\\u4e09\\u90e8\\u83f2\\u5c14\\u5e55\\u51fa\\u54c1\\u7684\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\uff0c\\u8fd9\\u4e5f\\u591f\\u6050\\u6016\\u7684\\uff0c\\u8fd8\\u662f\\u90a3\\u53e5...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8578229\\/\",\n" +
                "            \"id\": \"8578229\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 1.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"\\u8fd9lj\\u7535\\u5f71\\u6211\\u8fd8\\u662f\\u53bb\\u7535\\u5f71\\u9662\\u770b\\u7684    \\u770b\\u4e8620\\u5206\\u949f\\u6211\\u5c31\\u51fa\\u6765\\u4e86\\u4ec0\\u4e48j8\\u73a9\\u610f\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"u43434343\",\n" +
                "                \"avatar\": \"http://img3.doubanio.com\\/icon\\/user_normal.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/u43434343\\/\",\n" +
                "                \"id\": \"85207511\",\n" +
                "                \"name\": \"\\u6211\\u662f\\u4f20\\u5947\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u8fd9lj\\u7535\\u5f71\\u6211\\u8fd8\\u662f\\u53bb\\u7535\\u5f71\\u9662\\u770b\\u7684    \\u770b\\u4e8620\\u5206\\u949f\\u6211\\u5c31\\u51fa\\u6765\\u4e86 \\u4ec0\\u4e48j8\\u73a9\\u610f \\u8fd9lj\\u7535\\u5f71\\u6211\\u8fd8\\u662f\\u53bb\\u7535\\u5f71\\u9662\\u770b\\u7684    \\u770b\\u4e8620\\u5206\\u949f\\u6211\\u5c31\\u51fa\\u6765\\u4e86 \\u4ec0\\u4e48j8\\u73a9\\u610f \\u8fd9lj\\u7535\\u5f71\\u6211\\u8fd8\\u662f\\u53bb\\u7535\\u5f71\\u9662\\u770b\\u7684    \\u770b\\u4e8620\\u5206\\u949f\\u6211\\u5c31\\u51fa\\u6765\\u4e86 \\u4ec0\\u4e48j8\\u73a9\\u610f \\u8fd9lj\\u7535\\u5f71\\u6211...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/9605462\\/\",\n" +
                "            \"id\": \"9605462\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 5.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"\\u6211\\u770b\\u5230\\u4e86\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\u7684\\u65b0\\u5e0c\\u671b\\uff01\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"177624181\",\n" +
                "                \"avatar\": \"http://img7.doubanio.com\\/icon\\/u177624181-3.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/177624181\\/\",\n" +
                "                \"id\": \"177624181\",\n" +
                "                \"name\": \"Shamless\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u8bf4\\u53e5\\u826f\\u5fc3\\u8bdd\\uff0c\\u6050\\u6016\\u7247\\u6211\\u770b\\u4e86\\u4e5f\\u4e0d\\u4e0b\\u4e00\\u4e07\\u90e8\\u4e86\\uff0c\\u53e4\\u4eca\\u4e2d\\u5916\\u65e0\\u6240\\u4e0d\\u89c8\\u3002\\u6ca1\\u529e\\u6cd5\\uff0c\\u4eba\\u95f2\\u662f\\u975e\\u591a\\uff0c\\u5c31\\u627e\\u70b9\\u6050\\u6016\\u7247\\u6253\\u53d1\\u65f6\\u95f4\\u3002\\u53ef\\u662f\\u8c5a\\u9f20\\u7cfb\\u5217\\uff0c\\u5927\\u5934\\u602a\\u5a74\\uff0c\\u4e0b\\u6c34\\u9053\\u6740\\u624b\\uff0c\\u7b14\\u4ed9\\u8d1e\\u5b50\\u789f\\u4ed9\\uff0c\\u804a\\u658b\\u5438\\u8840\\u9b3c\\u8352\\u6751\\uff0c\\u603b\\u4e4b\\u5404\\u79cd\\u5404\\u6837\\u7684\\u5427\\uff0c\\u6211\\u662f\\u6ca1\\u6709\\u89c1\\u8fc7...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/9315542\\/\",\n" +
                "            \"id\": \"9315542\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 1.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"\\u5dee\\u5230\\u4e0d\\u884c\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"158559795\",\n" +
                "                \"avatar\": \"http://img3.doubanio.com\\/icon\\/user_normal.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/158559795\\/\",\n" +
                "                \"id\": \"158559795\",\n" +
                "                \"name\": \"\\u4f9d\\u65e7\\u7b9c\\u7d54\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u771f\\u7684\\u5f88\\u70c2 \\u5f88\\u70c2 \\u6210\\u4e86\\u559c\\u5267 \\u5982\\u679c\\u8bc4\\u8bba\\u6d89\\u53ca\\u7535\\u5f71\\u548c\\u5c0f\\u8bf4\\u7684\\u7ed3\\u5c40\\u548c\\u5173\\u952e\\u60c5\\u8282\\uff0c\\u8bf7\\u52fe\\u9009\\u300c\\u6709\\u5173\\u952e\\u60c5\\u8282\\u900f\\u9732\\u300d\\uff0c\\u8c46\\u74e3\\u5c06\\u663e\\u793a\\u63d0\\u793a\\uff0c\\u4ee5\\u514d\\u6ca1\\u6709\\u770b\\u8fc7\\u7684\\u4eba\\u626b\\u5174\\u3002  \\u4e3a\\u4e86\\u5c0a\\u91cd\\u521b\\u4f5c\\u8005\\u7684\\u52b3\\u52a8\\uff0c\\u8bf7\\u4e0d\\u8981\\u8f6c\\u8f7d\\u4ed6\\u4eba\\u6587\\u7ae0\\u6216\\u63d0\\u4f9b\\u4e0b\\u8f7d\\u4fe1\\u606f\\u3002\\u8c46\\u74e3\\u9f13\\u52b1\\u6709\\u76ca...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8394178\\/\",\n" +
                "            \"id\": \"8394178\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 1.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"2017\\u5e741\\u670814\\u65e5\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"106658069\",\n" +
                "                \"avatar\": \"http://img7.doubanio.com\\/icon\\/u106658069-4.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/106658069\\/\",\n" +
                "                \"id\": \"106658069\",\n" +
                "                \"name\": \"\\u4e24\\u4e24\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u5c0f\\u840c\\u8bf4\\u8981\\u53bb\\u770b\\uff0c\\u4ece\\u5934\\u5230\\u5c3e\\u5168\\u662f\\u69fd\\u70b9\\uff0c\\u8fd9\\u5267\\u672c\\u65e0\\u8bba\\u600e\\u4e48\\u62cd\\u90fd\\u4e0d\\u4f1a\\u597d\\u4e86\\u2026\\u602a\\u4e0d\\u5f97\\u9093sir\\u5bf9\\u6211\\u5199\\u7684\\u9b3c\\u6545\\u4e8b\\u5982\\u6b64\\u6709\\u4fe1\\u5fc3\\uff0c\\u56e0\\u4e3a\\u5927\\u5bb6\\u90fd\\u662f\\u8fd9\\u6c34\\u5e73\\u5417\\u2026 \\u4e0d\\u8fc7\\u8001\\u5b9e\\u8bf4\\uff0c\\u8fd9\\u4e2a\\u7f16\\u5267\\u72af\\u7684\\u9519\\u8bef\\u6211\\u4e5f\\u72af\\u8fc7\\uff1a\\u6545\\u4e8b\\u548c\\u7ebf\\u7d22\\u4e0d\\u96c6\\u4e2d\\u3002\\u5199\\u300a\\u6740\\u4eba\\u72af\\u300b\\u7684\\u65f6\\u5019\\uff0c...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8823383\\/\",\n" +
                "            \"id\": \"8823383\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 1.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"\\u8001\\u5957\\u8def\\u6ca1\\u521b\\u610f\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"149343489\",\n" +
                "                \"avatar\": \"http://img7.doubanio.com\\/icon\\/u149343489-1.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/149343489\\/\",\n" +
                "                \"id\": \"149343489\",\n" +
                "                \"name\": \"\\ud83d\\uddff\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u70c2\\u7247 \\u5608\\u70b9\\u592a\\u591a\\u4e86\\u597d\\u5417 \\u524d\\u9762\\u521a\\u5f00\\u59cb\\u6709\\u9b3c\\u51fa\\u73b0 \\u540e\\u9762\\u5927\\u90e8\\u5206\\u90fd\\u662f\\u60c5\\u611f\\u620f \\u6700\\u540e\\u7ed3\\u679c\\u53c8\\u662f\\u4eba\\u4e3a\\u626e\\u9b3c \\u5f88\\u591a\\u73b0\\u8c61\\u4e5f\\u662f\\u65e0\\u6cd5\\u89e3\\u91ca\\u7684 \\u7535\\u4e3a\\u4ec0\\u4e48\\u8bf4\\u505c\\u5c31\\u505c \\u4e3a\\u4ec0\\u4e48\\u91cc\\u9762\\u7684\\u4eba\\u53ef\\u4ee5\\u8f7b\\u677e\\u627e\\u5230\\u6a21\\u7279\\u5398\\u7c73\\u7684\\u4ee3\\u53f7\\uff1f \\u6bcf\\u4e2a\\u4eba\\u90a3\\u4e48\\u5bb9\\u6613\\u8ba4\\u51fa\\u81ea\\u5df1\\u7684\\u624b\\u638c\\u5370 \\uff1f...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8278482\\/\",\n" +
                "            \"id\": \"8278482\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 1.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"?\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"154276285\",\n" +
                "                \"avatar\": \"http://img7.doubanio.com\\/icon\\/u154276285-1.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/154276285\\/\",\n" +
                "                \"id\": \"154276285\",\n" +
                "                \"name\": \"\\ud83d\\udc67\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u8d85\\u7ea7\\u70c2\\u7247\\uff0c\\u8ba9\\u5979\\u7239\\u73a9\\u4e00\\u5bbf\\uff0c\\u9884\\u544a\\u7247\\u526a\\u8f91\\u4e0d\\u9519\\uff0c\\u6b64\\u7247\\u770b\\u5b8c\\u9884\\u544a\\u7247\\u5373\\u53ef\\uff0c\\u770b\\u4e86\\u591a\\u4f59\\uff0c\\u6f0f\\u6d1e\\u767e\\u51fa\\uff0c\\u7a7f\\u5e2e\\u955c\\u5934\\u65e0\\u6570\\uff0c\\u65e0\\u5398\\u5934\\u5230\\u4e86\\u6781\\u81f4\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002\\u3002...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8278145\\/\",\n" +
                "            \"id\": \"8278145\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 4.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"\\u300a\\u6050\\u6016\\u7406\\u53d1\\u5e97\\u300b\\uff1a\\u9752\\u4e1d\\u72b9\\u5728\\uff0c\\u9b42\\u9b44\\u5df2\\u98de\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"41576647\",\n" +
                "                \"avatar\": \"http://img7.doubanio.com\\/icon\\/u41576647-3.jpg\",\n" +
                "                \"signature\": \"\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/41576647\\/\",\n" +
                "                \"id\": \"41576647\",\n" +
                "                \"name\": \"\\u4e11\\u9c7c\\u5c3c\\u83ab\"\n" +
                "            },\n" +
                "            \"summary\": \"\\u300a\\u6050\\u6016\\u7406\\u53d1\\u5e97\\u300b\\u8bb2\\u8ff0\\u7684\\u662f\\u4e00\\u4e2a\\u53d1\\u751f\\u5728\\u7406\\u53d1\\u5e97\\u7684\\u7075\\u5f02\\u4e8b\\u4ef6\\uff0c\\u800c\\u7075\\u5f02\\u7684\\u80cc\\u540e\\uff0c\\u603b\\u6709\\u4e00\\u4e9b\\u8bf4\\u4e0d\\u6e05\\u9053\\u4e0d\\u660e\\u7684\\u771f\\u76f8\\u5728\\u4f5c\\u795f\\u3002\\u4f46\\u662f\\uff0c\\u5f53\\u771f\\u76f8\\u4e00\\u70b9\\u70b9\\u6c34\\u843d\\u77f3\\u51fa\\u7684\\u65f6\\u5019\\uff0c\\u53c8\\u603b\\u4f1a\\u53eb\\u4eba\\u5fc3\\u60b8\\u3001\\u60ca\\u53a5\\uff0c\\u6bdb\\u9aa8\\u609a\\u7136\\uff0c\\u4e0d\\u5bd2\\u800c\\u6817\\u7684\\u611f\\u89c9\\u4e5f\\u6084\\u4e0a\\u5fc3\\u5934\\u3002  \\u8352\\u5c71...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8239886\\/\",\n" +
                "            \"id\": \"8239886\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"rating\": {\n" +
                "                \"max\": 5,\n" +
                "                \"value\": 4.0,\n" +
                "                \"min\": 0\n" +
                "            },\n" +
                "            \"title\": \"Word\\u5929\\u5440\\uff01\\u4ee5\\u540e\\u518d\\u4e5f\\u4e0d\\u6562\\u53bb\\u7406\\u53d1\\u5e97\\u4e86\",\n" +
                "            \"subject_id\": \"26865690\",\n" +
                "            \"author\": {\n" +
                "                \"uid\": \"70359207\",\n" +
                "                \"avatar\": \"http://img3.doubanio.com\\/icon\\/u70359207-8.jpg\",\n" +
                "                \"signature\": \"\\u767e\\u5ea6\\u767e\\u5bb6\\u3001\\u4eca\\u65e5\\u5934\\u6761\\u4f5c\\u5bb6\\u3001\\u5f71\\u8bc4\\u4eba\",\n" +
                "                \"alt\": \"http:\\/\\/www.douban.com\\/people\\/70359207\\/\",\n" +
                "                \"id\": \"70359207\",\n" +
                "                \"name\": \"\\u5927\\u4f83\"\n" +
                "            },\n" +
                "            \"summary\": \"  \\u60ca\\u609a\\u3001\\u6050\\u6016\\u7c7b\\u7684\\u5f71\\u7247\\uff0c\\u6bcf\\u5468\\u90fd\\u5728\\u5f71\\u9662\\u91cc\\u73b0\\u8eab\\uff0c\\u4e0d\\u4f46\\u6709\\u56fa\\u5b9a\\u7684\\u6d88\\u8d39\\u7fa4\\u4f53\\u548c\\u53d7\\u4f17\\uff0c\\u8fd8\\u65f6\\u4e0d\\u65f6\\u7075\\u5149\\u4e00\\u95ea\\u5728\\u7968\\u623f\\u4e0a\\u521b\\u51fa\\u4f73\\u7ee9\\uff0c\\u300a\\u6050\\u6016\\u6e38\\u6cf3\\u9986\\u300b\\u3001\\u300a\\u5e8a\\u4e0b\\u6709\\u4eba\\u300b\\u3001\\u300a\\u6795\\u8fb9\\u6709\\u5f20\\u8138\\u300b\\u7b49\\u90fd\\u662f\\u5176\\u4e2d\\u7684\\u4ee3\\u8868\\u3002\\u5f53\\u4e0b\\uff0c\\u89c2\\u4f17\\u7684\\u6b23\\u8d4f\\u53e3\\u5473\\u4e0d\\u65ad\\u63d0\\u5347\\uff0c...\",\n" +
                "            \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8239440\\/\",\n" +
                "            \"id\": \"8239440\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"ratings_count\": 653,\n" +
                "    \"aka\": [\n" +
                "        \"Ghost in Barber's\"\n" +
                "    ]\n" +
                "}"
    }

    fun get_strFilmPhoto(): String {
        return "{\n" +
                "  \"count\": 1,\n" +
                "  \"photos\": [\n" +
                "    {\n" +
                "      \"photos_count\": 12,\n" +
                "      \"thumb\": \"https://img3.doubanio.com/view/photo/thumb/public/p2406383762.jpg\",\n" +
                "      \"icon\": \"https://img3.doubanio.com/view/photo/icon/public/p2406383762.jpg\",\n" +
                "      \"author\": {\n" +
                "        \"uid\": \"122971558\",\n" +
                "        \"avatar\": \"https://img3.doubanio.com/icon/u122971558-2.jpg\",\n" +
                "        \"signature\": \"\",\n" +
                "        \"alt\": \"https://www.douban.com/people/122971558/\",\n" +
                "        \"id\": \"122971558\",\n" +
                "        \"name\": \"轮子\"\n" +
                "      },\n" +
                "      \"created_at\": \"2016-12-18 20:14:19\",\n" +
                "      \"album_id\": \"1638319514\",\n" +
                "      \"cover\": \"https://img3.doubanio.com/view/photo/albumcover/public/p2406383762.jpg\",\n" +
                "      \"id\": \"2406383762\",\n" +
                "      \"prev_photo\": \"2408074715\",\n" +
                "      \"album_url\": \"https://movie.douban.com/subject/26865690/photos\",\n" +
                "      \"comments_count\": 2,\n" +
                "      \"image\": \"https://img3.doubanio.com/view/photo/photo/public/p2406383762.jpg\",\n" +
                "      \"recs_count\": 1,\n" +
                "      \"position\": 6,\n" +
                "      \"alt\": \"https://movie.douban.com/photos/photo/2406383762/\",\n" +
                "      \"album_title\": \"恐怖理发店(26865690)\",\n" +
                "      \"next_photo\": \"2406383761\",\n" +
                "      \"subject_id\": \"26865690\",\n" +
                "      \"desc\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 17,\n" +
                "  \"start\": 0,\n" +
                "  \"subject\": {\n" +
                "    \"rating\": {\n" +
                "      \"max\": 10,\n" +
                "      \"average\": 2.7,\n" +
                "      \"details\": {\n" +
                "        \"1\": 27,\n" +
                "        \"3\": 0,\n" +
                "        \"2\": 3,\n" +
                "        \"5\": 2,\n" +
                "        \"4\": 0\n" +
                "      },\n" +
                "      \"stars\": \"15\",\n" +
                "      \"min\": 0\n" +
                "    },\n" +
                "    \"genres\": [\n" +
                "      \"爱情\",\n" +
                "      \"悬疑\",\n" +
                "      \"惊悚\"\n" +
                "    ],\n" +
                "    \"title\": \"恐怖理发店\",\n" +
                "    \"casts\": [\n" +
                "      {\n" +
                "        \"avatars\": {\n" +
                "          \"small\": \"https://img1.doubanio.com/img/celebrity/small/1403756298.69.jpg\",\n" +
                "          \"large\": \"https://img1.doubanio.com/img/celebrity/large/1403756298.69.jpg\",\n" +
                "          \"medium\": \"https://img1.doubanio.com/img/celebrity/medium/1403756298.69.jpg\"\n" +
                "        },\n" +
                "        \"name_en\": \"Guoer Yin\",\n" +
                "        \"name\": \"殷果儿\",\n" +
                "        \"alt\": \"https://movie.douban.com/celebrity/1340984/\",\n" +
                "        \"id\": \"1340984\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"avatars\": {\n" +
                "          \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
                "          \"large\": \"https://img3.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
                "          \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
                "        },\n" +
                "        \"name_en\": \"Qingan Ren\",\n" +
                "        \"name\": \"任青安\",\n" +
                "        \"alt\": \"https://movie.douban.com/celebrity/1359164/\",\n" +
                "        \"id\": \"1359164\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"avatars\": {\n" +
                "          \"small\": \"https://img3.doubanio.com/img/celebrity/small/1451209491.55.jpg\",\n" +
                "          \"large\": \"https://img3.doubanio.com/img/celebrity/large/1451209491.55.jpg\",\n" +
                "          \"medium\": \"https://img3.doubanio.com/img/celebrity/medium/1451209491.55.jpg\"\n" +
                "        },\n" +
                "        \"name_en\": \"SungGoo Kang\",\n" +
                "        \"name\": \"姜星丘\",\n" +
                "        \"alt\": \"https://movie.douban.com/celebrity/1353667/\",\n" +
                "        \"id\": \"1353667\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"durations\": [\n" +
                "      \"89分钟\"\n" +
                "    ],\n" +
                "    \"collect_count\": 348,\n" +
                "    \"mainland_pubdate\": \"2017-01-06\",\n" +
                "    \"has_video\": false,\n" +
                "    \"original_title\": \"恐怖理发店\",\n" +
                "    \"subtype\": \"movie\",\n" +
                "    \"directors\": [\n" +
                "      {\n" +
                "        \"avatars\": {\n" +
                "          \"small\": \"https://img1.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png\",\n" +
                "          \"large\": \"https://img3.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png\",\n" +
                "          \"medium\": \"https://img1.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png\"\n" +
                "        },\n" +
                "        \"name_en\": \"Shilei Lu\",\n" +
                "        \"name\": \"陆诗雷\",\n" +
                "        \"alt\": \"https://movie.douban.com/celebrity/1360707/\",\n" +
                "        \"id\": \"1360707\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"pubdates\": [\n" +
                "      \"2017-01-06(中国大陆)\"\n" +
                "    ],\n" +
                "    \"year\": \"2017\",\n" +
                "    \"images\": {\n" +
                "      \"small\": \"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2406903891.jpg\",\n" +
                "      \"large\": \"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2406903891.jpg\",\n" +
                "      \"medium\": \"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2406903891.jpg\"\n" +
                "    },\n" +
                "    \"alt\": \"https://movie.douban.com/subject/26865690/\",\n" +
                "    \"id\": \"26865690\"\n" +
                "  }\n" +
                "}"
    }

    fun get_strFilmReview(): String {
        return "{\"count\": 1, \"reviews\": [{\"rating\": {\"max\": 5, \"value\": 1.0, \"min\": 0}, \"useful_count\": 38, \"author\": {\"uid\": \"123404248\", \"avatar\": \"https://img3.doubanio.com\\/icon\\/u123404248-5.jpg\", \"signature\": \"\", \"alt\": \"https:\\/\\/www.douban.com\\/people\\/123404248\\/\", \"id\": \"123404248\", \"name\": \"\\u4e16\\u754c\\u5947\\u5999\\u7269\\u8bed\"}, \"created_at\": \"2017-01-19 21:46:33\", \"title\": \"\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\uff0c\\u6ce8\\u5b9a\\u6210\\u70c2\\u7247\\uff1f\", \"updated_at\": \"2017-04-12 15:52:33\", \"share_url\": \"https:\\/\\/m.douban.com\\/movie\\/review\\/8301338\", \"summary\": \"\\u8fd9\\u4e00\\u7cfb\\u5217\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\u592a\\u591a\\uff0c\\u73b0\\u5728\\u603b\\u7ed3\\u4e0b\\u56fd\\u4ea7\\u7535\\u5f71\\u62cd\\u6444\\u95e8\\u69db\\u4e3a\\u4ec0\\u4e48\\u8fd9\\u4e48\\u4f4e\\u2026\\u2026 1.\\u627e\\u4e2a\\u5bfc\\u6f14\\uff0c\\u5185\\u5730\\u5bfc\\u6f14\\u4f18\\u5148\\u8003\\u8651(\\u7701\\u94b1)\\u3002 2.\\u53bb\\u7f51\\u4e0a\\u70ed\\u641c\\u699c\\uff08\\u4e5f\\u662f\\u7ecf\\u7eaa\\u516c\\u53f8\\uff09\\u4e0a\\u6311\\u51e0\\u4e2a\\u7f51\\u7ea2\\u660e\\u661f\\uff08\\u7701\\u94b1\\uff09\\u3002\\u7f51\\u7ea2\\u660e\\u661f\\u5c31\\u50cf\\u6728\\u5076\\u4e00\\u6837\\u88ab\\u88c5\\u626e\\u4e0a\\u4e86\\u3002 3.\\u53bb...\", \"content\": \"\\u8fd9\\u4e00\\u7cfb\\u5217\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\u592a\\u591a\\uff0c\\u73b0\\u5728\\u603b\\u7ed3\\u4e0b\\u56fd\\u4ea7\\u7535\\u5f71\\u62cd\\u6444\\u95e8\\u69db\\u4e3a\\u4ec0\\u4e48\\u8fd9\\u4e48\\u4f4e\\u2026\\u2026\\r\\n1.\\u627e\\u4e2a\\u5bfc\\u6f14\\uff0c\\u5185\\u5730\\u5bfc\\u6f14\\u4f18\\u5148\\u8003\\u8651(\\u7701\\u94b1)\\u3002\\r\\n2.\\u53bb\\u7f51\\u4e0a\\u70ed\\u641c\\u699c\\uff08\\u4e5f\\u662f\\u7ecf\\u7eaa\\u516c\\u53f8\\uff09\\u4e0a\\u6311\\u51e0\\u4e2a\\u7f51\\u7ea2\\u660e\\u661f\\uff08\\u7701\\u94b1\\uff09\\u3002\\u7f51\\u7ea2\\u660e\\u661f\\u5c31\\u50cf\\u6728\\u5076\\u4e00\\u6837\\u88ab\\u88c5\\u626e\\u4e0a\\u4e86\\u3002\\r\\n3.\\u53bb\\u5f71\\u89c6\\u5b66\\u9662\\u548c\\u5317\\u6f02\\u5357\\u6f02\\u627e\\u4e0a\\u5176\\u4ed6\\u6f14\\u5458\\u9f99\\u5957\\uff08\\u7701\\u94b1\\uff09\\u3002\\r\\n4.\\u79df\\u4e2a\\u6444\\u5236\\u7ec4\\uff0c\\u62cd\\u6444\\u573a\\u5730\\uff0c\\u6f14\\u51fa\\u670d\\u3002\\uff08\\u5982\\u4e0a\\uff09\\r\\n5.\\u627e\\u4e8c\\u4e09\\u7ebf\\u7f16\\u5267\\uff08\\u5982\\u4e0a\\uff09\\u6216\\u81ea\\u5df1\\u64cd\\u5200\\u82b1\\u4e0a\\u4e00\\u661f\\u671f\\u628a\\u5267\\u672c\\u51d1\\u5230\\u4e00\\u90e8\\u7535\\u5f71\\u65f6\\u957f\\uff08\\u53c2\\u8003\\u7f51\\u4e0a\\u5267\\u672c\\u7684\\u5bf9\\u8bdd\\uff09 \\r\\n6.\\u62cd\\u5b8c\\uff0c\\u5c31\\u526a\\u8f91\\u621089\\u5206\\u949f\\u5de6\\u53f3\\u7684\\u7535\\u5f71\\u957f\\u5ea6\\u3002\\r\\n7.\\u6253\\u5305\\u7ed9\\u8425\\u9500\\u516c\\u53f8\\uff0c\\u76ee\\u6807\\u662f\\u5404\\u6837\\u9a6c\\u51f3\\u5360\\u9886\\u5404\\u641c\\u7d22\\u5fae\\u535a\\u7b49\\u9635\\u5730(\\u7535\\u5f71\\u8e6d\\u70ed\\u5ea6)\\u3002\\u5185\\u5bb9\\u4e3b\\u9898\\u53ea\\u8981\\u4e0d\\u53cd\\u52a8\\u4e0d\\u72af\\u7f6a\\u968f\\u4fbf\\u6362\\uff0c\\u4e00\\u5c0f\\u65f6\\u51fa\\u73b0\\u5728\\u9875\\u9762\\u524d3\\u6761\\u3002\\r\\n8.\\u5168\\u56fd\\u5404\\u5730\\u5f71\\u9662\\u6392\\u6863\\u671f\\u4e0a\\u6620\\u4e86\\uff08\\u7535\\u5f71\\u9662\\u6392\\u7247\\u591a\\u4e8e\\u9e3f\\u6bdb\\uff0c\\u5206\\u6210\\u4e0d\\u5c11\\uff09\\u3002\\r\\n\\u4e00\\u8def\\u8fc7\\u6765\\u7684\\u53ea\\u8981\\u9075\\u5faa\\u56fd\\u5bb6\\u6cd5\\u5f8b\\u5236\\u5ea6\\uff0c\\u907f\\u5f00\\u884c\\u4e1a\\u4e0d\\u6210\\u6587\\u7684\\u7981\\u533a\\u57fa\\u672c\\u7eff\\u706f\\u901a\\u8fc7\\u3002\\u54ea\\u6015\\u662f\\u4f26\\u7406\\u9053\\u5fb7\\uff0c\\u903b\\u8f91\\u89c4\\u5f8b\\u3002\\u4e0d\\u89e6\\u72af\\u6cd5\\u5f8b\\u8981\\u6c42\\u4e5f\\u662f\\u53ef\\u4ee5\\u653e\\u4f4e\\u7684\\uff0c\\u5bfc\\u6f14\\u5c31\\u662f\\u4e0a\\u5e1d\\u3002\\r\\n\\r\\n\\u6700\\u540e\\u603b\\u7ed3\\uff1a\\u5982\\u679c\\u7b97\\u662f\\u521b\\u4f5c\\u7684\\u8bdd\\uff0c\\u9664\\u4e86\\u539f\\u8457\\u7528\\u4e86\\u8111\\u529b\\uff0c\\u642d\\u4e0a\\u52b3\\u529b\\uff0c\\u4e3b\\u8981\\u9760\\u8d44\\u6e90\\u914d\\u7f6e\\uff0c\\u5c31\\u53ef\\u4ee5\\u516c\\u653e\\u6536\\u94b1\\u4e86\\u3002 \\r\\n\\r\\nps:\\u56fd\\u4ea7\\u6050\\u6016\\u7247\\u7684\\u7ec4\\u6210\\uff1a15%\\u7684\\u521b\\u4f5c+65%\\u7684\\u94b1+20%\\u7684\\u52b3\\u529b=\\u4f5c\\u54c1\", \"useless_count\": 1, \"comments_count\": 2, \"alt\": \"https:\\/\\/movie.douban.com\\/review\\/8301338\\/\", \"id\": \"8301338\", \"subject_id\": \"26865690\"}], \"start\": 0, \"total\": 13, \"next_start\": 1, \"subject\": {\"rating\": {\"max\": 10, \"average\": 2.4, \"details\": {\"1\": 71.0, \"3\": 3.0, \"2\": 6.0, \"5\": 1.0, \"4\": 0.0}, \"stars\": \"15\", \"min\": 0}, \"genres\": [\"\\u7231\\u60c5\", \"\\u60ac\\u7591\", \"\\u60ca\\u609a\"], \"title\": \"\\u6050\\u6016\\u7406\\u53d1\\u5e97\", \"casts\": [{\"avatars\": {\"small\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\", \"large\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\", \"medium\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\"}, \"name_en\": \"Guoer Yin\", \"name\": \"\\u6bb7\\u679c\\u513f\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1340984\\/\", \"id\": \"1340984\"}, {\"avatars\": {\"small\": \"https://img1.doubanio.com\\/f\\/movie\\/ca527386eb8c4e325611e22dfcb04cc116d6b423\\/pics\\/movie\\/celebrity-default-small.png\", \"large\": \"https://img3.doubanio.com\\/f\\/movie\\/63acc16ca6309ef191f0378faf793d1096a3e606\\/pics\\/movie\\/celebrity-default-large.png\", \"medium\": \"https://img1.doubanio.com\\/f\\/movie\\/8dd0c794499fe925ae2ae89ee30cd225750457b4\\/pics\\/movie\\/celebrity-default-medium.png\"}, \"name_en\": \"Qingan Ren\", \"name\": \"\\u4efb\\u9752\\u5b89\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1359164\\/\", \"id\": \"1359164\"}, {\"avatars\": {\"small\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1451209491.55.webp\", \"large\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1451209491.55.webp\", \"medium\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1451209491.55.webp\"}, \"name_en\": \"SungGoo Kang\", \"name\": \"\\u59dc\\u661f\\u4e18\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1353667\\/\", \"id\": \"1353667\"}], \"durations\": [\"89\\u5206\\u949f\"], \"collect_count\": 707, \"mainland_pubdate\": \"2017-01-06\", \"has_video\": true, \"original_title\": \"\\u6050\\u6016\\u7406\\u53d1\\u5e97\", \"subtype\": \"movie\", \"directors\": [{\"avatars\": {\"small\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\", \"large\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\", \"medium\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\"}, \"name_en\": \"Shilei Lu\", \"name\": \"\\u9646\\u8bd7\\u96f7\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1360707\\/\", \"id\": \"1360707\"}], \"pubdates\": [\"2017-01-06(\\u4e2d\\u56fd\\u5927\\u9646)\"], \"year\": \"2017\", \"images\": {\"small\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\", \"large\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\", \"medium\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\"}, \"alt\": \"https:\\/\\/movie.douban.com\\/subject\\/26865690\\/\", \"id\": \"26865690\"}}"
    }

    fun get_strFilmComment(): String {
        return "{\"count\": 1, \"comments\": [{\"rating\": {\"max\": 5, \"value\": 1.0, \"min\": 0}, \"useful_count\": 276, \"author\": {\"uid\": \"62598926\", \"avatar\": \"https://img3.doubanio.com\\/icon\\/u62598926-1.jpg\", \"signature\": \"\", \"alt\": \"https:\\/\\/www.douban.com\\/people\\/62598926\\/\", \"id\": \"62598926\", \"name\": \"\\u5c0f\\u66e6\\u8ba8\\u538c\\u4e0b\\u96e8\\u5929\"}, \"subject_id\": \"26865690\", \"content\": \"\\u592a\\u6050\\u6016\\u4e86\\uff01\\u5413\\u5f97\\u6211\\u4ece\\u7b2c\\u5341\\u5206\\u949f\\u5f00\\u59cb\\u5c31\\u6ca1\\u6562\\u7741\\u773c\\u775b\\uff0c\\u6700\\u540e\\u76f4\\u63a5\\u7761\\u7740\\u4e86\\u3002\", \"created_at\": \"2017-01-06 15:16:12\", \"id\": \"1132938490\"}], \"start\": 0, \"total\": 323, \"next_start\": 1, \"subject\": {\"rating\": {\"max\": 10, \"average\": 2.4, \"details\": {\"1\": 71.0, \"3\": 3.0, \"2\": 6.0, \"5\": 1.0, \"4\": 0.0}, \"stars\": \"15\", \"min\": 0}, \"genres\": [\"\\u7231\\u60c5\", \"\\u60ac\\u7591\", \"\\u60ca\\u609a\"], \"title\": \"\\u6050\\u6016\\u7406\\u53d1\\u5e97\", \"casts\": [{\"avatars\": {\"small\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\", \"large\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\", \"medium\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1403756298.69.webp\"}, \"name_en\": \"Guoer Yin\", \"name\": \"\\u6bb7\\u679c\\u513f\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1340984\\/\", \"id\": \"1340984\"}, {\"avatars\": {\"small\": \"https://img1.doubanio.com\\/f\\/movie\\/ca527386eb8c4e325611e22dfcb04cc116d6b423\\/pics\\/movie\\/celebrity-default-small.png\", \"large\": \"https://img3.doubanio.com\\/f\\/movie\\/63acc16ca6309ef191f0378faf793d1096a3e606\\/pics\\/movie\\/celebrity-default-large.png\", \"medium\": \"https://img1.doubanio.com\\/f\\/movie\\/8dd0c794499fe925ae2ae89ee30cd225750457b4\\/pics\\/movie\\/celebrity-default-medium.png\"}, \"name_en\": \"Qingan Ren\", \"name\": \"\\u4efb\\u9752\\u5b89\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1359164\\/\", \"id\": \"1359164\"}, {\"avatars\": {\"small\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1451209491.55.webp\", \"large\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1451209491.55.webp\", \"medium\": \"https://img3.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1451209491.55.webp\"}, \"name_en\": \"SungGoo Kang\", \"name\": \"\\u59dc\\u661f\\u4e18\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1353667\\/\", \"id\": \"1353667\"}], \"durations\": [\"89\\u5206\\u949f\"], \"collect_count\": 707, \"mainland_pubdate\": \"2017-01-06\", \"has_video\": true, \"original_title\": \"\\u6050\\u6016\\u7406\\u53d1\\u5e97\", \"subtype\": \"movie\", \"directors\": [{\"avatars\": {\"small\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\", \"large\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\", \"medium\": \"https://img1.doubanio.com\\/view\\/celebrity\\/s_ratio_celebrity\\/public\\/p1490348628.29.webp\"}, \"name_en\": \"Shilei Lu\", \"name\": \"\\u9646\\u8bd7\\u96f7\", \"alt\": \"https:\\/\\/movie.douban.com\\/celebrity\\/1360707\\/\", \"id\": \"1360707\"}], \"pubdates\": [\"2017-01-06(\\u4e2d\\u56fd\\u5927\\u9646)\"], \"year\": \"2017\", \"images\": {\"small\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\", \"large\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\", \"medium\": \"https://img3.doubanio.com\\/view\\/photo\\/s_ratio_poster\\/public\\/p2406903891.webp\"}, \"alt\": \"https:\\/\\/movie.douban.com\\/subject\\/26865690\\/\", \"id\": \"26865690\"}}"
    }

    fun test_FilmList(film: FilmList) {
        assertNotNull(film)
        assertNotNull(film.subjects)
        assert(film.subjects.any())
        assertNotNull(film.subjects[0].casts)
        assertNotNull(film.subjects[0].directors)
        assertNotNull(film.subjects[0].directors)
        assertNotNull(film.subjects[0].rating)
        assertNotNull(film.subjects[0].rating.average)
    }

    fun test_FilmDetail(film: FilmDetail) {
        assertNotNull(film)
        assertNotNull(film.summary)
        assert(film.casts.any())
        assertNotNull(film.casts[0].id)
        assertNotNull(film.casts[0].avatars)
        assertNotNull(film.casts[0].name)
        assertNotNull(film.photos)
        assert(film.photos.any())
        assertNotNull(film.photos[0].thumb)
        assertNotNull(film.rating.details)
        assertNotNull(film.rating.details.star1)
    }

    fun test_FilmPhoto(film: FilmPhoto) {
        assertNotNull(film)
        assertNotNull(film.count)
        assertNotNull(film.photos)
        assert(film.photos.any())
        assertNotNull(film.subject)
        assertNotNull(film.subject.id)
        assertNotNull(film.subject.casts)
        assert(film.subject.casts.any())
    }

    fun test_FilmReview(film: FilmReview) {
        assertNotNull(film)
        assertNotNull(film.count)
        assertNotNull(film.reviews)
        assert(film.reviews.any())
        assertNotNull(film.reviews[0].content)
        assertNotNull(film.reviews[0].summary)
        assertNotNull(film.reviews[0].rating)
        assertNotNull(film.total)
    }

    fun test_FilmComment(film: FilmComment) {
        assertNotNull(film)
        assertNotNull(film.count)
        assertNotNull(film.comments)
        assert(film.comments.any())
        assertNotNull(film.comments[0].useful_count)
        assertNotNull(film.comments[0].content)
        assertNotNull(film.comments[0].rating)
        assertNotNull(film.total)
    }

    @Test
    fun test_gson_parse_FilmDetail_fromLocal() {
        val film: FilmDetail = Gson().fromJson(strFilmDetail)
        test_FilmDetail(film)
    }

    @Test
    fun test_gson_parse_FilmList_fromLocal() {
        val film: FilmList = Gson().fromJson(strFilmList)
        test_FilmList(film)
    }

    @Test
    fun test_getTheaterFilm_fromNet() {
        val film = Douban.getTheaterFilms("北京")
        test_FilmList(film)
    }

    @Test
    fun test_getFilmDetail_fromNet() {
        val film = Douban.getFilmDetail("26309969")
        test_FilmDetail(film)
    }

    @Test
    fun test_getFilmDetail_fromLocal() {
        val film: FilmDetail = Gson().fromJson(strFilmDetail)
        test_FilmDetail(film)
    }

    @Test
    fun test_getFilmPhoto_fromNet() {
        val film = Douban.getFilmPhoto("26865690")
        test_FilmPhoto(film)
    }

    @Test
    fun test_getFilmPhoto_fromLocal() {
        val film: FilmPhoto = Gson().fromJson(strFilmPhoto)
        test_FilmPhoto(film)
    }

    @Test
    fun test_getFilmReview_fromNet() {
        val film = Douban.getFilmReview("26865690")
        test_FilmReview(film)
    }

    @Test
    fun test_getFilmReview_fromLocal() {
        val film: FilmReview = Gson().fromJson(strFilmReview)
        test_FilmReview(film)
    }

    @Test
    fun test_getFilmComment_fromNet() {
        val film = Douban.getFilmComment("26636712")
        test_FilmComment(film)
    }

    @Test
    fun test_getFilmComment_fromLocal() {
        val film: FilmComment = Gson().fromJson(strFilmComment)
        test_FilmComment(film)
    }

}