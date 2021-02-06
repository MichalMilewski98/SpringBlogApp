INSERT INTO USERS (id, password, email, username, active)
VALUES
  (1, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user@mail.com', 'user', 1);
-- password in plaintext: "password"
INSERT INTO USERS (id, password, email, username, active)
VALUES
  (2, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'johndoe@gmail.com', 'johndoe', 1);
-- password in plaintext: "password"
INSERT INTO USERS (id, password, email, username, active)
VALUES (3, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'ana@mail.com', 'ana', 1);

-- Roles
INSERT INTO ROLES (id, role)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLES (id, role)
VALUES (2, 'ROLE_USER');

-- User Roles
INSERT INTO USER_ROLES (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLES (user_id, role_id)
VALUES (1, 2);
INSERT INTO USER_ROLES (user_id, role_id)
VALUES (2, 2);
INSERT INTO USER_ROLES (user_id, role_id)
VALUES (3, 2);

-- Posts
INSERT INTO POSTS (id, title, post_content, isprivate)
VALUES (1, 'Title 1',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry.
         Lorem Ipsum has been the industrys standard dummy text ever since the 1500s,
          when an unknown printer took a galley of type and scrambled it to make a type specimen book.
           It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
             and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                Sed ullamcorper dolor vel elit vulputate, vel molestie ante pellentesque. Morbi lectus tellus, vestibulum at quam id,
                 mollis efficitur est. Duis viverra, dolor at sollicitudin finibus, quam odio aliquet magna, at vestibulum neque sapien ac libero.
                   Curabitur sagittis dignissim mauris, at varius mi aliquam a. Phasellus aliquam, nisl eu dictum aliquet, justo lectus pharetra diam,
                    ac hendrerit lacus ligula ut massa. Nulla mattis lacus quis eros vehicula pellentesque. Quisque id tortor vulputate tellus convallis vestibulum id sit amet purus.', 0);
INSERT INTO POSTS (id, title, post_content, isprivate)
VALUES (2, 'Title 2',
'Lorem Ipsum is simply dummy text of the printing and typesetting industry.
         Lorem Ipsum has been the industrys standard dummy text ever since the 1500s,
          when an unknown printer took a galley of type and scrambled it to make a type specimen book.
           It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
             and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                Sed ullamcorper dolor vel elit vulputate, vel molestie ante pellentesque. Morbi lectus tellus, vestibulum at quam id,
                 mollis efficitur est. Duis viverra, dolor at sollicitudin finibus, quam odio aliquet magna, at vestibulum neque sapien ac libero.
                   Curabitur sagittis dignissim mauris, at varius mi aliquam a. Phasellus aliquam, nisl eu dictum aliquet, justo lectus pharetra diam,
                    ac hendrerit lacus ligula ut massa. Nulla mattis lacus quis eros vehicula pellentesque. Quisque id tortor vulputate tellus convallis vestibulum id sit amet purus.', 1);
INSERT INTO POSTS (id, title, post_content, isprivate)
VALUES (3, 'Title 3',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry.
         Lorem Ipsum has been the industrys standard dummy text ever since the 1500s,
          when an unknown printer took a galley of type and scrambled it to make a type specimen book.
           It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
             and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                Sed ullamcorper dolor vel elit vulputate, vel molestie ante pellentesque. Morbi lectus tellus, vestibulum at quam id,
                 mollis efficitur est. Duis viverra, dolor at sollicitudin finibus, quam odio aliquet magna, at vestibulum neque sapien ac libero.
                   Curabitur sagittis dignissim mauris, at varius mi aliquam a. Phasellus aliquam, nisl eu dictum aliquet, justo lectus pharetra diam,
                    ac hendrerit lacus ligula ut massa. Nulla mattis lacus quis eros vehicula pellentesque. Quisque id tortor vulputate tellus convallis vestibulum id sit amet purus.', 0);
INSERT INTO POSTS (id, title, post_content, isprivate)
VALUES (4, 'Title 4',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry.
         Lorem Ipsum has been the industrys standard dummy text ever since the 1500s,
          when an unknown printer took a galley of type and scrambled it to make a type specimen book.
           It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
             and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                Sed ullamcorper dolor vel elit vulputate, vel molestie ante pellentesque. Morbi lectus tellus, vestibulum at quam id,
                 mollis efficitur est. Duis viverra, dolor at sollicitudin finibus, quam odio aliquet magna, at vestibulum neque sapien ac libero.
                   Curabitur sagittis dignissim mauris, at varius mi aliquam a. Phasellus aliquam, nisl eu dictum aliquet, justo lectus pharetra diam,
                    ac hendrerit lacus ligula ut massa. Nulla mattis lacus quis eros vehicula pellentesque. Quisque id tortor vulputate tellus convallis vestibulum id sit amet purus.', 1);

INSERT INTO POST_AUTHORS (post_id, user_id)
VALUES (1, 1);
INSERT INTO POST_AUTHORS (post_id, user_id)
VALUES (1, 2);
INSERT INTO POST_AUTHORS (post_id, user_id)
VALUES (2, 2);
INSERT INTO POST_AUTHORS (post_id, user_id)
VALUES (3, 2);
INSERT INTO POST_AUTHORS (post_id, user_id)
VALUES (4, 3);


INSERT INTO TAGS (id, name)
VALUES (1, 'tag1');
INSERT INTO TAGS (id, name)
VALUES (2, 'tag2');
INSERT INTO TAGS (id, name)
VALUES (3, 'tag3');
INSERT INTO TAGS (id, name)
VALUES (4, 'tag4');

INSERT INTO POST_TAGS (post_id, tag_id)
VALUES (1, 1);
INSERT INTO POST_TAGS (post_id, tag_id)
VALUES (1, 2);
INSERT INTO POST_TAGS (post_id, tag_id)
VALUES (2, 2);
INSERT INTO POST_TAGS (post_id, tag_id)
VALUES (3, 3);


-- Comments
INSERT INTO COMMENTS (id, post_id, user_id, body)
VALUES (1, 1, 1,
        'Lorem');
INSERT INTO COMMENTS (id, post_id, user_id, body)
VALUES (2, 2, 2,
        'Lorem2');
INSERT INTO COMMENTS (id, post_id, user_id, body)
VALUES (3, 3, 3,
        'Lorem3 ');
