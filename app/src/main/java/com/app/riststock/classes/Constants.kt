package com.app.riststock.classes

import com.app.riststock.MainActivity


object Constants {

    const val LOCAL = "local"
    const val FACEBOOK = "facebook"
    const val TWITTER = "twitter"

    const val Arabic = "ar"
    const val English = "en"

    const val Points_To_SAR = 100
    const val TIME_ZONE = "GMT+3"
    const val COUNTRY_CODE = "00966"

    const val LOCATION_MY = "my"
    const val LOCATION_CITY = "city"
    const val NO_CONNECTION = "no connection"
    const val KEY_FILE = "Stock_Rist_file"

    const val HEADER_FONT = "play_fair_display_bold.ttf"
//    val MEDIUM_FONT = if (UtilityApp.language == English) "Poppins_Medium.otf" else "sky_medium.otf"
//    val NORMAL_FONT = if (UtilityApp.language == English) "Poppins_Regular.otf" else "sky_reg.ttf"
//    val BOLD_FONT = if (UtilityApp.language == English) "Poppins_Bold.otf" else "sky_bold.ttf"

    val MEDIUM_FONT = "opensans_medium.ttf"
    val NORMAL_FONT = "opensans-regular.ttf"
    val BOLD_FONT = "opensans_bold.ttf"
    const val ICON_AWSM_FONT = "fa-light-300.otf"
    const val ICON_AWSM_FONT2 = "fontawesome_webfont.ttf"
    const val ICON_MOON_FONT = "icomoon.ttf"

    val MAIN_ACTIVITY = MainActivity::class.java

    const val TOKEN_PREFIX = "Bearer "
    const val GUEST_TOKEN_PREFIX = "device_id "
    const val GUEST_UUID_PREFIX = "Bearer "
    const val UNAUTHORIZED = "UnauthorizedHttpException"

    const val ERROR = "error"
    const val NULL = "null"
    const val FAIL = "fail"
    const val SUCCESS = "success"

    const val URL_FACEBOOK = "https://www.facebook.com/dcs.qu"
    const val URL_TWITTER = "https://twitter.com/comuservice"
    const val APP_EMAIL = "info@qudcss.com"

    const val PAYTABS_MERCHANT_EMAIL = "ism.sh@hotmail.com"
    const val PAYTABS_SECRET_KEY =
        "3Upm9a7Rt2EqrjXeYmnrXShWo6W983AcX0y7aGYDIC4aW1oUnMmlFDh3JdcSgN4eNDmYjkB2XGcTHBO4naq2NH6xUiqb5vqT8MRs"
    const val PAYTABS_SITE_URL = "https://thorba2.sahaba.tech/"
    const val PAYTABS_RETURN_URL = "https://thorba2.sahaba.tech/success_payment"

    const val REFRESH_RESERVATIONS_LIST = "refresh_reservations_list"
    const val REFRESH_MAIN_FAVOURITE = "refresh_main_favourite"
    const val REFRESH_FAVOURITE_LIST = "refresh_favourite_list"

    const val FB_NAME = "name"
    const val FB_AVATAR = "avatar"
    const val FB_RATING = "rating"
    const val FB_MOBILE = "mobile"
    const val FB_STATUS = "status"
    const val FB_CURRENT_ORDER_ID = "current_order_id"
    const val FB_LAT = "lat"
    const val FB_LNG = "lng"
    const val FB_DRIVERS = "Drivers"


    const val CAT_COURSES = 5
    const val CAT_EXAMS = 2
    const val CAT_BOOKS = 1
    const val CAT_FAQS = 4
    const val CAT_VIDEOS = 5

    const val LOW = "Low"
    const val NORMAL = "Normal"
    const val HIGH = "High"

    const val MAX_HOUR_INTERVAL = 15

    const val ORDERS_ACTIVE = "order_active"
    const val ORDERS_ENDED = "order_ended"
    const val RESERVATION_ACTIVE = "reservation_active"
    const val RESERVATION_ENDED = "reservation_ended"
    const val SLIDER_CATEGORY = "slider"
    const val FOOD_CATEGORY = "food"
    const val TAG_CATEGORY = "tag"
    const val SEARCH_CATEGORY = "search"
    const val CANCEL_COUNTER = "counter"
    const val CANCEL_USER = "user"
    const val RESERVATION_SCHEDULE = "reservations"
    const val RESERVATION_WAIT_LIST = "waitlist"
    const val RESERVATION_NON = "none"
    const val QR_CODE = "qrcode-reservation"
    const val CATEGORY_EVENT = "events"
    const val RESERVATION_REVIEW = "reservation_review"
    const val RESERVATION = "reservation"
    const val RESTAURANT = "restaurants"
    const val LOCAL_DETAILS = "local"
    const val ONLINE_DETAILS = "online"
    const val SORT = "sort"
    const val ADD_TO_FAV_LOGIN = "add_to_fav_login"
    const val ADD_REVIEW_LOGIN = "add_review_login"
    const val NORMAL_LOGIN = "normal_login"

    const val MALE = "m"
    const val FEMALE = "f"

    const val FRAG_MENU = "frag_menu"
    const val FRAG_REVIEWS = "frag_reviews"
    const val FRAG_SETTING = "frag_setting"
    const val FRAG_SUPPORT = "frag_support"
    const val FRAG_CUSTOMER_REVIEWS = "frag_customer_review"
    const val FRAG_COMPLAINTS_LIST = "frag_complaints_list"
    const val FRAG_EDIT_PROFILE = "frag_edit_profile"
    const val FRAG_ABOUT = "frag_about"
    const val FRAG_MY_BILLS = "frag_my_bills"

    const val KEY_MEMBER = "key_member"
    const val KEY_FIREBASE_TOKEN = "firebase_token"
    const val KEY_MEMBER_LOCATION = "key_member_location"
    const val KEY_MY_SEARCH_DATA = "key_my_search_data"
    const val KEY_MEMBER_LANGUAGE = "key_member_language"
    const val KEY_URL = "KEY_URL"
    const val KEY_FIRST_OPEN = "key_first_open"
    const val KEY_CURRENT_LOCATION = "key_current_location"
    const val KEY_IS_SEND_FCM = "key_is_send_fcm"
    const val KEY_IS_LOCATION_STARTED = "key_is_location_started"

    const val KEY_CATEGORY_ID = "key_category_id"
    const val KEY_COURSE_ID = "key_course_id"
    const val KEY_COURSE_MODEL = "key_course_model"
    const val KEY_CATEGORY_NAME = "key_category_name"
    const val KEY_IS_RATE_APP = "key_is_rate_app"
    const val KEY_IS_KEYBOARD_VISIBLE = "KEY_IS_KEYBOARD_VISIBLE"
    const val KEY_IMAGES_POS = "key_images_pos"
    const val KEY_IMAGE_URL = "key_image_url"
    const val KEY_IS_FAST_LOGIN = "key_is_fast_login"
    const val KEY_VIP_PREFIX = "vip_"
    const val KEY_USER_NAME = "key_user_name"
    const val KEY_AVATAR_URL = "key_avatar_url"
    const val KEY_USER_MODEL = "key_user_model"

    //    public static final String KEY_GUEST_TOKEN = "key_guest_token";
    const val KEY_IS_GUEST_CONFIRM = "key_is_guest_confirm"
    const val KEY_RESTAURANT_ID = "key_restaurant_id"
    const val KEY_RESTAURANT_TYPE = "key_restaurant_id"
    const val KEY_Restaurant_DETAILS_TYPE = "key_Restaurant_details_type"
    const val KEY_COMPANY_MODEL = "key_company_model"
    const val KEY_LOGIN_REQUEST = "key_login_request"
    const val KEY_TOKEN = "key_token"
    const val KEY_USER_TYPE = "key_user_type"

    //    public static final String KEY_COMPANY_STATUS = "key_company_status";
    const val KEY_GUEST_TOKEN = "key_guest_token"
    const val KEY_GUEST_MOBILE_MODEL = "key_guest_mobile_model"
    const val KEY_TICKET_ID = "key_ticket_id"

    //    public static final String KEY_COMPANY_OPEN_TO = "key_company_open_to";
    const val KEY_MUST_INVITE_GUEST = "key_must_invite_guest"
    const val KEY_TITLE = "key_title"
    const val KEY_CATEGORY_TYPE = "key_category_type"
    const val KEY_BOOK_PERSON = "people"
    const val KEY_BOOK_CHILDS = "party_size_children"
    const val KEY_BOOK_DATE_TIME = "datetime"
    const val KEY_BOOK_DAY_STR = "key_book_day_str"
    const val KEY_BOOK_DAY_NUMBER = "key_book_day_number"
    const val KEY_BOOK_HOUR_STR = "key_book_hour_str"
    const val KEY_BOOK_DATE_TIME_STR = "key_datetime_str"
    const val KEY_BOOK_LOCATION_STR = "key_location_str"
    const val KEY_BOOK_FLOOR_ID = "floor_id"
    const val KEY_BOOK_FLOOR_NAME = "key_floor_name"
    const val KEY_BOOK_FLOORS_LIST = "key_floors_list"
    const val KEY_RESERVATION_GUEST_LIST = "key_reservation_guest_list"
    const val KEY_BOOK_SPECIAL_REQUEST = "key_specail_request"
    const val KEY_BOOK_OCCASION = "key_occasion"
    const val KEY_IS_RESERVATION_EDIT = "key_is_reservation_edit"
    const val KEY_BOOK_LOCATION_TYPE = "key_location_type"
    const val KEY_PICKER_DAY_POS = "key_picker_day_pos"
    const val KEY_PICKER_HOUR_POS = "key_picker_hour_pos"
    const val KEY_PICKER_DAY_STR = "key_day_str"

    //    public static final String KEY_PICKER_DAY = "key_day";
    const val KEY_PICKER_HOUR = "key_hour"
    const val KEY_RESERVATION_NOTIFIY = "key_is_reservation_notify"
    const val KEY_CITY_ID = "key_city_id"
    const val KEY_LAT = "key_lat"
    const val KEY_LNG = "key_lng"
    const val KEY_MAP_ZOOM = "key_map_zoom"
    const val KEY_SEARCH_TEXT = "key_search_text"
    const val KEY_SEARCH_CUISINES = "key_search_cuisines"
    const val KEY_SEARCH_TAGS = "key_search_rags"
    const val KEY_QRCODE_URL = "key_qrcode_url"

    //    public static final String KEY_SEARCH_PERSONS = "key_search_person";
    const val KEY_BOOK_MODEL = "key_book_model"
    const val KEY_FIRST_NAME = "key_first_name"
    const val KEY_LAST_NAME = "key_last_name"
    const val KEY_COUNTRY_CODE = "key_country_code"
    const val KEY_MOBILE = "key_mobile"
    const val KEY_BIRTHDAY = "key_birthday"
    const val KEY_ANNIVERSARY = "key_anniversary"
    const val KEY_EMAIL = "key_email"
    const val KEY_IS_PAY_FEES = "key_is_pay_fees"
    const val KEY_VIP_NOTE = "key_vip_note"
    const val KEY_RESTURANT_ACCEPT_RETAINER = "key_resturant_accept_retainer"
    const val KEY_PARTY_SIZE = "key_party_size"
    const val KEY_FEES_VALUE = "key_fees_value"
    const val KEY_FEES_TYPE = "key_fees_type"
    const val KEY_ORDER_TYPE = "key_order_type"
    const val KEY_RESERVATION_ID = "key_reservation_id"
    const val KEY_REQUEST_ID = "key_request_id"
    const val KEY_ORDER_ID = "key_order_id"
    const val KEY_ORDER_MODEL = "key_order_model"
    const val KEY_CHAT_ID = "key_chat_id"
    const val KEY_OFFER_PRICE = "key_offer_price"

    //    const val KEY_DELIVERY_PRICE = "key_delivery_price"
    const val KEY_CANCEL_MINUTES = "key_cancel_minutes"
    const val KEY_CREATE_TIME = "key_create_time"
    const val KEY_CURRENT_TIME = "key_current_time"
    const val KEY_FOOD_RATE = "key_food_rate"
    const val KEY_SERVICE_RATE = "key_service_rate"
    const val KEY_AMBIENCE_RATE = "key_ambience_rate"
    const val KEY_PRICE_RATE = "key_price_rate"
    const val KEY_PUBLIC_COMMENT = "key_public_comment"
    const val KEY_PRIVATE_COMMENT = "key_private_comment"
    const val KEY_ATTACH_PHOTOS = "key_attach_photos"
    const val KEY_FRAGMENT_TYPE = "key_fragment_type"
    const val KEY_IS_NOTIFY = "key_is_notify"
    const val KEY_FILTERS_LIST_MODEL = "key_filters_list_model"
    const val KEY_OPEN_CALENDER = "key_open_calender"
    const val KEY_CALENDER_MODEL = "key_calender_model"
    const val KEY_LOGIN_PREFERANCE = "key_login_preferance"

    /** */

    const val DB_CategoriesModel = "db_categories"
    const val DB_FaqsModel = "db_faqs"
    const val DB_SettingsModel = "db_settings"
    const val DB_CitiesModel = "db_cities"
    const val DB_ConfigModel = "db_config"
    const val DB_CountryCodes = "db_country_codes"

    const val CAPTURE = "capture"
    const val PICK = "pick"
    const val SAVE = "save"
    const val CLEAR = "clear"
    const val code = "code"
    const val barcode = "barcode"





}
