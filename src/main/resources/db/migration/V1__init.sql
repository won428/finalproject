-- Flyway V2 baseline schema (cleaned from mysqldump --no-data)
-- Notes:
-- 1) This script creates tables first, then adds foreign keys at the end to avoid dependency ordering issues.
-- 2) DO NOT include DROP TABLE in versioned migrations; use Flyway clean in dev if you need resets.

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================
-- Tables (no foreign keys)
-- ======================

CREATE TABLE `ad_campaigns` (
`contract_amount` decimal(15,2) NOT NULL,
  `end_date` date NOT NULL,
  `start_date` date NOT NULL,
  `created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(20) DEFAULT 'ACTIVE',
  `type` varchar(20) NOT NULL,
  `client_name` varchar(100) NOT NULL,
  `title` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `board` (
  `board_id` bigint NOT NULL AUTO_INCREMENT,
  `board_code` varchar(30) NOT NULL,
  `board_name` varchar(50) NOT NULL,

  `is_active` tinyint(1) NOT NULL DEFAULT 1,

  PRIMARY KEY (`board_id`),

  UNIQUE KEY `uk_board_board_code` (`board_code`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `board_status` (
  `board_id` bigint NOT NULL,
  `status_code` varchar(30) NOT NULL,

  `label` varchar(30) NOT NULL,
  `sort_order` int NOT NULL,

  `is_filterable` tinyint(1) NOT NULL DEFAULT 1,
  `is_default` tinyint(1) NOT NULL DEFAULT 0,

  `default_marker` VARCHAR(30) GENERATED ALWAYS AS (CASE WHEN is_default = 1 THEN status_code ELSE NULL END) STORED,

  PRIMARY KEY (`board_id`, `status_code`),

  UNIQUE KEY `uk_board_status_one_default` (`board_id`, `default_marker`),
  KEY `idx_board_status_board_sort` (`board_id`, `sort_order`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chat_flows` (
`display_order` int DEFAULT 0,
  `is_active` bit(1) DEFAULT 1 NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL,
  `next_node_id` bigint NULL,
  `node_type` varchar(20) NOT NULL,
  `action_code` varchar(50) DEFAULT NULL,
  `button_text` varchar(100) DEFAULT NULL,
  `response_message` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_parent_order` (`parent_id`, `display_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chat_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `session_id` bigint NOT NULL,
  `sender_type` varchar(20) NOT NULL,
  `sender_user_id` bigint DEFAULT NULL,

  `message_type` varchar(20) NOT NULL DEFAULT 'TEXT',
  `content` TEXT NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),

  KEY `idx_msg_session_created` (`session_id`, `created_at`),
  KEY `idx_msg_session_id` (`session_id`, `id`),

  KEY `fk_msg_session` (`session_id`),
  KEY `fk_msg_sender_user` (`sender_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chat_read_state` (
  `session_id` bigint NOT NULL,
  `reader_user_id` bigint NOT NULL,
  `last_read_message_id` bigint DEFAULT NULL,
  `last_read_at` datetime(6) DEFAULT NULL,

  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (`session_id`, `reader_user_id`),

  KEY `idx_read_state_last_msg` (`last_read_message_id`),
  KEY `idx_read_state_reader` (`reader_user_id`, `session_id`),

  KEY `fk_read_state_session` (`session_id`),
  KEY `fk_read_state_reader` (`reader_user_id`),
  KEY `fk_read_state_last_message` (`last_read_message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chat_session` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `counselor_id` bigint DEFAULT NULL,

  `open_key` varchar(36) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'WAITING',
  `close_reason` varchar(50) DEFAULT NULL,
  `ended_by` varchar(20) DEFAULT NULL,

  `queued_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,

  `assigned_at` datetime(6) DEFAULT NULL,
  `started_at` datetime(6) DEFAULT NULL,
  `ended_at` datetime(6) DEFAULT NULL,

  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_session_open_key` (`open_key`),

  KEY `idx_session_user_created` (`user_id`, `created_at`),
  KEY `idx_session_counselor_created` (`counselor_id`, `created_at`),
  KEY `idx_session_status` (`status`),
  KEY `idx_session_status_queued` (`status`, `queued_at`),
  KEY `idx_session_counselor_status` (`counselor_id`, `status`, `created_at`),

  KEY `fk_session_user` (`user_id`),
  KEY `fk_session_counselor` (`counselor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  `parent_comment_id` bigint DEFAULT NULL,

  `content` TEXT NOT NULL,

  `is_answer` tinyint NOT NULL DEFAULT 0,

  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,

  `deleted_at` datetime(6) DEFAULT NULL,

  PRIMARY KEY (`comment_id`),

  -- 인덱스
  KEY `ix_comment_post_created` (`post_id`, `created_at`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `counselor_absence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `counselor_id` bigint NOT NULL,

  `start_at` datetime(6) NOT NULL,
  `end_at` datetime(6) DEFAULT NULL,
  `reason` varchar(200) NOT NULL,
  `created_by` bigint DEFAULT NULL,

  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),

  KEY `idx_absence_counselor_range` (`counselor_id`, `start_at`, `end_at`),

  KEY `fk_absence_counselor` (`counselor_id`),
  KEY `fk_absence_created_by` (`created_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `counselor_profile` (
  `counselor_id` bigint NOT NULL,
  `max_concurrent_chats` int NOT NULL DEFAULT 3,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,

  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (`counselor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `coupon` (
  `discount` int NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `expiration_date` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `coupon_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_coupon_coupon_name` (`coupon_name`),
  CONSTRAINT `coupon_chk_1` CHECK (((`discount` <= 100) and (`discount` >= 1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `coupon_cart` (
  `coupon_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `received_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'UNUSED',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_coupon_cart_user_coupon` (`user_id`,`coupon_id`),
  KEY `fk_coupon_cart_coupon` (`coupon_id`),
  KEY `fk_coupon_cart_users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `course_att` (
  `courses_curriculum_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `file_size_bytes` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `checksum` varchar(64) NOT NULL,
  `s3_key` varchar(1024) NOT NULL,
  `content_type` varchar(255) NOT NULL,
  `original_file_name` varchar(255) NOT NULL,
  `storage_provider` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_course_att_courses_curriculum_id` (`courses_curriculum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `course_history` (
  `changed_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `changed_by` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` bigint NOT NULL,
  `revision_no` bigint NOT NULL,
  `course_name` varchar(100) NOT NULL,
  `action` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_course_history_course_revision` (`course_id`,`revision_no`),

  KEY `idx_course_history_changed_by` (`changed_by`),
  KEY `idx_course_history_changed_at` (`changed_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
  `coupon_id` bigint DEFAULT NULL,
  `discount_amount` bigint DEFAULT NULL,
  `discount_percent` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ordered_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `paid_amount` bigint DEFAULT NULL,
  `refunded_amount` bigint DEFAULT NULL,
  `total_amount` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `order_code` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_orders_order_code` (`order_code`),

  KEY `idx_orders_user_id` (`user_id`),
  KEY `idx_orders_coupon_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `course_order_items` (
`course_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `order_price` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_item_course` (`course_id`),
  KEY `fk_order_item_course_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_requests` (
  `course_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` bigint NOT NULL,
  `reviewed_at` datetime(6) DEFAULT NULL,
  `submitted_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `description` varchar(2000) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `idx_course_requests_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `course_reviews` (
  `star` tinyint NOT NULL,
  `course_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_reviews_user_course` (`user_id`,`course_id`),
  KEY `ix_course_reviews_course` (`course_id`),
  KEY `ix_course_reviews_user` (`user_id`),
  CONSTRAINT `course_reviews_chk_1` CHECK (((`star` <= 5) and (`star` >= 1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_sections` (
  `sort_order` int NOT NULL,
  `course_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_sort` (`course_id`,`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `courses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `course_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_course_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `courses_curriculum` (
`sort_order` int NOT NULL,
  `course_section_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_curriculum_section_sort` (`course_section_id`,`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `expert_applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reviewed_at` datetime(6) DEFAULT NULL,
  `reviewer_admin_id` bigint DEFAULT NULL,
  `submitted_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `reject_reason` varchar(200) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `fk_expert_applications_reviewer_admin` (`reviewer_admin_id`),
  KEY `fk_expert_applications_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `expert_applications_att` (
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `expert_application_id` bigint NOT NULL,
  `file_size_bytes` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `checksum` varchar(64) NOT NULL,
  `s3_key` varchar(1024) NOT NULL,
  `content_type` varchar(255) NOT NULL,
  `original_file_name` varchar(255) NOT NULL,
  `storage_provider` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_expert_app_att_application` (`expert_application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `financial_ledger` (
`amount` decimal(15,2) NOT NULL,
  `ad_campaign_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `settlement_id` bigint DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT CURRENT_TIMESTAMP,
  `transaction_type` varchar(10) NOT NULL,
  `note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_financial_ledger_ad_campaign` (`ad_campaign_id`),
  KEY `fk_financial_ledger_settlement` (`settlement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mentee_list` (
`end_date` date NOT NULL,
  `start_date` date NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mentee_id` bigint NOT NULL,
  `mentoring_post_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mentee_list_mentee` (`mentee_id`),
  KEY `fk_mentee_list_mentoring_post` (`mentoring_post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mentee_applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,

  `mentoring_post_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,

  `status` varchar(20) NOT NULL DEFAULT 'PENDING',
  `current_status` varchar(20) NOT NULL,
  `desired_field` varchar(20) NOT NULL,
  `goal` varchar(255) NOT NULL,

  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

  PRIMARY KEY (`id`),


  UNIQUE KEY `uk_mentee_applications_mentoring_posts_user` (`mentoring_post_id`, `user_id`),

  KEY `idx_mentee_applications_mentoring_posts` (`mentoring_post_id`),
  KEY `idx_mentee_applications_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `mentor_applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `processed_by` bigint DEFAULT NULL,
  `reviewed_at` datetime(6) DEFAULT NULL,
  `submitted_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `reject_reason` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `fk_mentor_application_processed_by` (`processed_by`),
  KEY `fk_mentor_application_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `mentoring_order_items` (
`id` bigint NOT NULL AUTO_INCREMENT,
  `mentoring_post_id` bigint NOT NULL,
  `order_id` bigint NOT NULL,
  `order_price` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mentoring_order_items_mentoring_post` (`mentoring_post_id`),
  KEY `fk_mentoring_order_items_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mentoring_post_tags` (
  `post_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`tag_id`),

  KEY `idx_mentoring_post_tags_tag_post` (`tag_id`,`post_id`)
  KEY `ix_mpt_tag` (`tag_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `mentoring_posts` (
  `capacity` int NOT NULL DEFAULT 1,
  `program_end_date` date NOT NULL,
  `program_start_date` date NOT NULL,
  `recruitment_end_date` date NOT NULL,
  `recruitment_start_date` date NOT NULL,
  `session_count` int NOT NULL,

  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

  `id` bigint NOT NULL AUTO_INCREMENT,
  `mentor_id` bigint NOT NULL,

  `title` varchar(200) NOT NULL,
  `content` mediumtext NOT NULL,
  `language_code` varchar(20) NOT NULL,

  PRIMARY KEY (`id`),

  KEY `idx_mentoring_posts_mentor_id` (`mentor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `mentors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `verified_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_mentors_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `payment` (
  `amount` bigint NOT NULL,
  `completed_at` datetime(6) DEFAULT NULL,
  `failed_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `requested_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `status` varchar(20) NOT NULL,
  `provider` varchar(30) NOT NULL,
  `provider_payment_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_payment_provider_payment_id` (`provider_payment_id`),

  KEY `fk_payment_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `policy` (
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `effective_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `published_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `locale` varchar(10) NOT NULL DEFAULT 'ko-KR',
  `status` varchar(20) NOT NULL DEFAULT 'DRAFT',
  `type` varchar(50) NOT NULL,
  `version` varchar(50) NOT NULL,
  `content` longtext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_policy` (`type`,`version`,`locale`),
  KEY `idx_policy_active` (`type`,`status`,`effective_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `board_id` bigint NOT NULL,
  `status_code` varchar(30) NOT NULL,
  `author_id` bigint NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` mediumtext NOT NULL,

  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,

  `view_count` int NOT NULL DEFAULT 0,
  `like_count` int NOT NULL DEFAULT 0,
  `comment_count` int NOT NULL DEFAULT 0,
  `answer_count` int NOT NULL DEFAULT 0,
  `accepted_comment_id` bigint DEFAULT NULL,

  PRIMARY KEY (`post_id`),

  -- 인덱스
  KEY `ix_post_board_created` (`board_id`, `created_at`),
  KEY `ix_post_board_status_created` (`board_id`, `status_code`, `created_at`),
  KEY `fk_post_author` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post_attachment` (
  `attachment_id` bigint NOT NULL AUTO_INCREMENT,

  -- [BaseAttEntity 매핑]
  `original_name` varchar(255) NOT NULL,
  `storage_provider` varchar(20) NOT NULL,
  `s3_key` varchar(255) NOT NULL,
  `content_type` varchar(100) NOT NULL,
  `file_size` bigint NOT NULL,
  `checksum` varchar(64) NOT NULL,
  `created_at` datetime(6) NOT NULL, -- BaseEntity의 설정에 따라 다름

  -- [PostAtt 필드]
  `post_id` bigint DEFAULT NULL,
  `file_url` varchar(500) NOT NULL,
  `is_image` tinyint(1) NOT NULL DEFAULT 0,
  `sort_order` int NOT NULL DEFAULT 0,
  `is_temp` tinyint(1) NOT NULL DEFAULT 1,

  PRIMARY KEY (`attachment_id`),
  KEY `ix_attachment_temp` (`is_temp`, `created_at`),
  KEY `fk_attachment_post` (`post_id`) -- FK 인덱스 (MySQL 자동생성)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post_tag` (
`post_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`tag_id`),
  KEY `ix_post_tag_tag` (`tag_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `refund` (
  `amount` bigint NOT NULL,
  `completed_at` datetime(6) DEFAULT NULL,
  `failed_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `payment_id` bigint NOT NULL,
  `requested_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `provider_refund_id` varchar(100) DEFAULT NULL,
  `fail_detail` varchar(500) DEFAULT NULL,
  `reason_detail` varchar(500) DEFAULT NULL,
  `fail_code` varchar(30) DEFAULT NULL,
  `reason_code` varchar(30) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_refund_provider_refund_id` (`provider_refund_id`),

  KEY `fk_refund_order` (`order_id`),
  KEY `fk_refund_payment` (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
`id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_role_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `settlement_items` (
`course_amount` decimal(15,2) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_id` bigint NOT NULL,
  `settlement_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_settlement_item_payment` (`payment_id`),
  KEY `fk_settlement_item_settlement` (`settlement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `settlements` (
`final_payout_amount` decimal(15,2) NOT NULL,
  `period_end` date NOT NULL,
  `period_start` date NOT NULL,
  `platform_fee_amount` decimal(15,2) NOT NULL,
  `platform_fee_rate` decimal(5,2) NOT NULL,
  `tax_amount` decimal(15,2) NOT NULL,
  `total_sales_amount` decimal(15,2) NOT NULL,
  `created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `instructor_id` bigint NOT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `idx_instructor_period` (`instructor_id`,`period_start`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `support_closure` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `start_at` datetime(6) NOT NULL,
  `end_at` datetime(6) NOT NULL,
  `reason` varchar(100) NOT NULL,
  `admin_id` bigint DEFAULT NULL,

  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),

  KEY `idx_closure_range` (`start_at`, `end_at`),
  KEY `fk_closure_created_by` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tag` (
`tag_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `tag_key` varchar(60) NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `uk_tag_tag_key` (`tag_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_consent` (
  `status` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `given_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `policy_id` bigint NOT NULL,
  `revoked_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `consent_type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_user_policy_history` (`user_id`,`policy_id`),

  KEY `fk_user_consent_user` (`user_id`),
  KEY `fk_user_consent_policy` (`policy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_entitlement_access` (
  `course_id` bigint NOT NULL,
  `course_order_item_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `granted_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_entitlement_access_course_user` (`course_id`,`user_id`),
  UNIQUE KEY `uk_user_entitlement_access_order_item` (`course_order_item_id`),
  KEY `idx_user_entitlement_access_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_entitlements` (
  `course_id` bigint NOT NULL,
  `course_order_item_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `id` bigint NOT NULL AUTO_INCREMENT,
  `revoked_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `revoke_reason` varchar(200) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'APPROVED',
  PRIMARY KEY (`id`),
  KEY `fk_user_entitlements_course` (`course_id`),
  KEY `fk_user_entitlements_course_order_item` (`course_order_item_id`),
  KEY `fk_user_entitlements_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_local_credentials` (
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `email_verified_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),

  UNIQUE KEY `uk_user_local_credentials_email` (`email`),
  KEY `idx_user_local_credentials_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_oauth_accounts` (
  `provider_email_verified` bit(1) NOT NULL DEFAULT b'0',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `id` bigint NOT NULL AUTO_INCREMENT,
  `linked_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `provider` varchar(20) NOT NULL,
  `provider_email` varchar(100) DEFAULT NULL,
  `provider_user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),

  UNIQUE KEY `uq_oauth_provider_id` (`provider`, `provider_user_id`),

  KEY `idx_oauth_user_id` (`user_id`),

  CONSTRAINT `fk_oauth_user`
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_reports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reporter_id` bigint NOT NULL,
  `post_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `reason` varchar(20) NOT NULL,
  `description` varchar(500) NOT NULL,

  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),

  KEY `idx_user_reports_reporter_id` (`reporter_id`),
  KEY `idx_user_reports_target_id` (`target_id`),
  KEY `idx_user_reports_post_id` (`post_id`),
  KEY `idx_user_reports_created_at` (`created_at`),

  KEY `fk_user_reports_reporter` (`reporter_id`),
  KEY `fk_user_reports_post` (`post_id`),
  KEY `fk_user_reports_target` (`target_id`),

  CONSTRAINT `ck_user_reports_target_at_least_one` CHECK (post_id IS NOT NULL OR target_id IS NOT NULL)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
  `granted_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_role_mapping` (`user_id`,`role_id`),
  KEY `idx_user_roles_user_id` (`user_id`),
  KEY `idx_user_roles_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_sanctions` (
  `admin_id` bigint NOT NULL,
  `expired_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `released_at` datetime(6) DEFAULT NULL,
  `started_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `user_id` bigint NOT NULL,
  `sanction_type` varchar(20) NOT NULL,
  `reason` text,
  PRIMARY KEY (`id`),

  KEY `idx_sanctions_user_expiry` (`user_id`,`expired_at`),
  KEY `fk_sanctions_admin` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_status_history` (
  `changed_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `changed_by` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `new_status` varchar(20) NOT NULL,
  `prev_status` varchar(20) NOT NULL,
  `change_reason` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),

  KEY `fk_status_history_admin` (`changed_by`),
  KEY `fk_status_history_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `users` (
`birth` date DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `deleted_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `last_login_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE',
  `nickname` varchar(50) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `address_detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

