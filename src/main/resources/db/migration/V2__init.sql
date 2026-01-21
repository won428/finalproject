ALTER TABLE post
ADD CONSTRAINT fk_post_accepted_comment
FOREIGN KEY (accepted_comment_id) REFERENCES comment(comment_id)
ON DELETE SET NULL;