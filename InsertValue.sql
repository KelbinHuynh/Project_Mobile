use petshop;

insert into role(role_name)
values
('Quản lý'),
('Khách hàng');

insert into gender(gender_name)
values
('Nam'),
('Nữ');

insert into `user`(username, `password`, name, gender_id, email, phone, role_id, addresses)
values
('baoadmin', 'Baoadmin123', 'Lê Đình Bảo', 1, 'baold@gmail.com', '0853928111', 1, '1 Võ Văn Ngân'),
('vuhuynhanh', 'Vuhuynhanh123', 'Huỳnh Anh Vũ', 1,  'lop9a1bs2@gmail.com', '0853928222', 2, '1 Võ Văn Ngân'),
('trinhphan', 'Trinhphan123', 'Phan Thị Diễm Trinh', 2, 'trinhpd@gmail.com', '0853928333', 2, '1 Võ Văn Ngân');

insert into status(status_name)
values
('Chưa giao'),
('Đang giao'),
('Đã giao'),
('Đã nhận');

insert into category(category_name, isDeleted)
values
('Chó', 0),
('Mèo', 0);-- ,
-- ('Đồ dùng', 0);

insert into styles(style_name, category_id)
values
('Mèo Anh', 2),
('Mèo chân ngắn', 2),
('Mèo Tai Cụp', 2),
('Chó Alaska Malamute', 1),
('Chó Beagle', 1),
('Chó Corgi', 1),
('Chó Golden Retriever', 1),
('Chó Husky Siberian', 1),
('Chó Phốc Sóc - Pomeranian', 1),
('Chó Poodle', 1),
('Chó Pug', 1),
('Chó Samoyed', 1);
-- ('Phụ kiện', 3),
-- ('Thực phẩm', 3),
-- ('Balo', 3),
-- ('Cát Vệ Sinh',3),
-- ('Chuồng',3);


insert into pets(pets_name, weight, age, gender, price, count, isActive, isDeleted)
values
('Mèo chân ngắn tai cụp yêu', 3.2, '2 tháng', 0, 30000000, 8, 1, 0),
('Mèo xám chân ngắn tai cụp siêu yêu', 2.2, '5 tháng', 1, 25000000, 5, 1, 0),
('Em mèo trắng đực siêu cưng', 2.6, '3 tháng', 1, 13000000, 10, 1, 0),
('Anh trai mèo Golden xinh trai', 3.2, '4 tháng', 1, 20000000, 3, 1, 0),
('Mèo anh lông ngắn cưng cưng', 2.5, '4 tháng', 0, 15000000, 1, 1, 0),
('Em mèo trắng cực phẩm', 3.0, '1 tuổi', 0, 28000000, 5, 1, 0),
('Mèo chân lùn đáng yêu', 2.4, '1 tuổi', 1, 25000000, 6, 1, 0),
('Mèo chân ngắn tai cụp', 2.1, '5 tháng', 0, 20000000, 4, 1, 0),
('Mèo Scottish Siluer', 2.2, '8 tháng', 0, 20000000, 6, 1, 0),
('Alaska hồng phấn siêu cute', 7.3, '5 tháng', 1, 18000000, 3, 1, 0),
('Alaska siêu cưng', 8, '2 tuổi', 0, 18000000, 2, 1, 0);

insert into pet_style(pets_id, pets_style)
values 
(1, 1),
(1, 2), 
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(4, 1),
(5, 1),
(6, 2),
(7, 2),
(8, 2),
(8, 3),
(9, 3),
(10, 4),
(11, 4);


insert into image_list_pets(pets_id, imageLink)
values
(1, '1_1.png'),
(1, '1_2.png'),
(1, '1_3.png'),
(1, '1_4.png'),
(2, '2_1.png'),
(2, '2_2.png'),
(2, '2_3.png'),
(2, '2_4.png'),
(3, '3_1.png'),
(3, '3_2.png'),
(3, '3_3.png'),
(3, '3_4.png'),
(4, '4_1.png'),
(4, '4_2.png'),
(4, '4_3.png'),
(4, '4_4.png'),
(5, '5_1.png'),
(5, '5_2.png'),
(5, '5_3.png'),
(5, '5_4.png'),
(6, '6_1.png'),
(6, '6_2.png'),
(6, '6_3.png'),
(7, '7_1.png'),
(7, '7_2.png'),
(7, '7_3.png'),
(7, '7_4.png'),
(8, '8_1.png'),
(8, '8_2.png'),
(8, '8_3.png'),
(8, '8_4.png'),
(9, '9_1.png'),
(9, '9_2.png'),
(9, '9_3.png'),
(9, '9_4.png'),
(10, '10_1.png'),
(10, '10_2.png'),
(10, '10_3.png'),
(10, '10_4.png'),
(11, '11_1.png'),
(11, '11_2.png'),
(11, '11_3.png'),
(11, '11_4.png');

-- insert into pet_foods(food_name, style_id, `description`, price, size, `count`, isActive, isDeleted)
-- values
-- ('Pate Catchy 5plus dành cho mèo', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Việt Nam

-- Thương hiệu: An Việt Tiến

-- Khối lượng: 70g

-- Nguyên liệu: thịt gà, nước, chất tạo hương, thạch, muối, dầu cá, prebiotic, fructooligosaccharide, vitamin E, taurine.

-- Sản phẩm có xuất xứ từ Việt Nam, là thức ăn cho mèo thơm ngon dinh dưỡng.

-- Công dụng: 
-- Cung cấp nguồn protein tự nhiên dễ hấp thu và đảm bảo đầy đủ chất dinh dưỡng.
-- Giúp xương chắc khoẻ và tăng sức để kháng.
-- Cải thiện hệ tiêu hoá.
-- Thạch mềm, dễ ăn, kích thích vị giác.
-- Taurine giúp bảo vệ tim và mắt của boss.
-- Prebiotic cải thiện hệ tiêu hoá cho boss.
-- Nguồn vitamin E, B12 cần thiết cho sự phát triển.

-- Cách Sử Dụng:
-- Cho mèo ăn trực tiếp hoặc trộn cùng hạt
-- Bảo quản nơi khô ráo, thoáng mát. Bịt kín miệng túi sau khi sử dụng.
-- Lượng thức ăn mỗi bữa có thể điều chỉnh phù hợp theo kích cỡ, độ tuổi, cân nặng và mức độ hoạt động của mèo.', 15000, 0.07, 100, 1, 0),
-- ('Hạt Royal Canin Persian Adult 2kg', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Pháp

-- Thương hiệu: Royal Canin – thương hiệu nổi tiếng trên toàn cầu về việc cung cấp dinh dưỡng sức khỏe vật nuôi.

-- Khối lượng: 2kg

-- Thành phần:

-- Protein gia cầm, chất béo động vật, gạo, protein thực vật*, bột bắp, xơ thực vật, protein động vật, gluten bắp, bột lúa mì, bắp, men, khoáng chất, rau diếp xoăn, dầu cá, psyllium (1%), dầu đậu nành, fructo-oligo-sacarit, men thủy phân  (nguồn manno-oligo-sacarit), dầu borage, chiết xuất cúc vạn thọ (nguồn lutein).

-- Phụ gia dinh dưỡng: Vitamin A, Vitamin D3, E1 (Sắt), E2 (i ốt), E4 (Đồng), E5 (Mangan), E6 (Kẽm), E8 (Selen) – Phụ gia kỹ thuật: Clinoptilolite – Chất chống oxi hóa.

-- *L.I.P.: Protein có độ tiêu hóa cao.', 525000, 2, 50, 1, 0),
-- ('Hạt Royal Canin Persian Kitteb 2kg', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Pháp

-- Thương hiệu: Royal Canin – thương hiệu nổi tiếng trên toàn cầu về việc cung cấp dinh dưỡng sức khỏe vật nuôi.

-- Khối lượng: 2kg

-- Thành phần:

-- Protein gia cầm, gạo, chất béo động vật, bắp, protein thực vật*, protein động vật, xơ thực vật, củ cải đường, men, dầu cái, dầu cá, dầu đậu nành, khoáng chất, psyllium (0,5%), fructo-oligo-sacarit, men thủy phân (nguồn manno-oligo-sacarit), chiết xuất men (nguồn betaglucan), dầu borage, chiết xuất cúc vạn thọ (nguồn lutein).

-- Phụ gia dinh dưỡng: Vitamin A, Vitamin D3, Vitamin E, E1 (Sắt), E2 (I ốt), E4 (Đồng), E5 (Mangan), E6 (Kẽm), E8 (Selen), Axit béo Omega 6: 49.9 g và Axit béo Omega 3: 10.6 g (trên mỗi kg) – Phụ gia kỹ thuật: Clinoptilolite – Chất chống oxi hóa.
-- *L.I.P.: Protein có độ tiêu hóa cao.
-- Cách Sử Dụng
-- Chỉ sử dụng cho mèo dưới 1 năm tuổi.
-- Đổ trực tiếp ra bát cho mèo ăn.
-- Bảo quản nơi khô ráo, thoáng mát. Bịt kín miệng túi sau khi lấy hạt ra.
-- Có thể ngâm mềm với canh / sữa tùy thích hoăc trộn thêm với thức ăn mềm, rau củ quả bổ sung theo khẩu vị của mèo.
-- Cho mèo cưng ăn Hạt Royal Canin Persian Kitten với khẩu phần thích hợp tuỳ theo mức độ hoạt động, cân nặng, độ tuổi của mèo (có hướng dẫn chi tiết trên bao bì sản phẩm hoặc tham khảo ý kiến của bác sĩ thú y).', 550000, 2, 100, 1, 0),
-- ('Hạt Royal Canin Kitten Brishtish Shorthair 2kg', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Pháp

-- Thương hiệu: Royal Canin – thương hiệu nổi tiếng trên toàn cầu về việc cung cấp dinh dưỡng sức khỏe vật nuôi.

-- Khối lượng: 2kg

-- Thành phần: Protein gia cầm đã khử nước, gạo, protein thực vật cô lập *, mỡ động vật, ngô, protein động vật thủy phân, gluten ngô, bột mì, củ cải đường, sợi thực vật, dầu cá, khoáng chất, dầu đậu nành, men và các bộ phận của chúng, vỏ trấu và hạt (0,5%), fructo-oligo-saccharides, men thủy phân (nguồn gốc của manno-oligosaccharides), chiết xuất men (nguồn betaglucans), dầu borage, chiết xuất cúc vạn thọ (nguồn lutein), động vật giáp xác thủy phân (nguồn glucosamine), sụn thủy phân (nguồn của chondroitin).

-- Hạt Royal Canin Kitten British Shorthair được sản xuất trên dây chuyền công nghệ hiện đại. Với những nguyên liệu chất lượng cao, cân bằng và đa dạng, giúp hỗ trợ mèo Anh lông ngắn phát triển toàn diện, khỏe mạnh.
-- Cách Sử Dụng
-- Chỉ sử dụng cho mèo Anh lông ngắn dưới 1 năm tuổi.
-- Đổ trực tiếp ra bát cho mèo ăn.
-- Bảo quản nơi khô ráo, thoáng mát. Bịt kín miệng túi sau khi lấy hạt ra.
-- Có thể ngâm mềm với canh / sữa tùy thích hoăc trộn thêm với thức ăn mềm, rau củ quả bổ sung theo khẩu vị của mèo.
-- Cho mèo cưng ăn Hạt Royal Canin Kitten British Shorthair với khẩu phần thích hợp tuỳ theo mức độ hoạt động, cân nặng, độ tuổi của mèo (có hướng dẫn chi tiết trên bao bì sản phẩm hoặc tham khảo ý kiến của bác sĩ thú y).', 600000, 2, 70, 1, 0),
-- ('Hạt Minino cho mèo 480g vị cá ngừ', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Pháp

-- Thương hiệu: Minino – thương hiệu nổi tiếng trên toàn cầu về việc cung cấp dinh dưỡng sức khỏe vật nuôi.

-- Khối lượng: 480g

-- Thành phần: Gạo, bột thịt gia cầm, lúa mì, bã đậu nành, mỡ gia cầm (nguồn tự nhiên của Omega 3 và 6), dầu cá), Taurine, khoáng chất, các vitamin A, E, B1,…

-- Hạt Minino cho mèo 480g vị cá ngừ được sản xuất trên dây chuyền công nghệ hiện đại. Với những nguyên liệu chất lượng cao, cân bằng và đa dạng, giúp hỗ trợ mèo phát triển toàn diện, khỏe mạnh, là sản phẩm thức ăn cho mèo bán chạy và được rất nhiều người chủ tin dùng.

-- Công Dụng 

-- Tăng cường hệ miễn dịch.
-- Thiết kế hạt nhỏ nhắn, dễ ăn.
-- Hỗ trợ hệ tiêu hóa.
-- Giúp đào thải lông vón cục.
-- Protein chất lượng cao.
-- Cách Sử Dụng

-- Đổ trực tiếp ra bát cho mèo ăn.
-- Bảo quản nơi khô ráo, thoáng mát. Bịt kín miệng túi sau khi lấy hạt ra.
-- Cho mèo cưng ăn Hạt Minino cho mèo 480g vị cá ngừ với khẩu phần thích hợp tuỳ theo mức độ hoạt động, cân nặng, độ tuổi của mèo (có hướng dẫn chi tiết trên bao bì sản phẩm hoặc tham khảo ý kiến của bác sĩ thú y).', 48000, 0.48, 100, 1, 0),

-- ('Hạt Smartheart Adult Roast Beef Flavor hương thịt bò nướng – 400g', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Thái Lan

-- Thương hiệu: SmartHeart – Thương hiệu thức ăn chứa hàm lượng dinh dưỡng tốt nhất dành cho cún cưng được sản xuất tại Thái Lan. Với nhiều dòng sản phẩm chuyên biệt với chức năng và chất dinh dưỡng được nghiên cứu đặc biệt. Nhằm đáp ứng nhu cầu sức khỏe và thể chất của những giống chó khác nhau.

-- Khối lượng: 400g

-- Hương vị: Thịt bò nướng

-- Hạt Smartheart Adult Roast Beef Flavor hương thịt bò nướng là loại thức ăn khô dạng hạt chuyên dụng giúp tạo cơ bắp cho các giống chó lớn đang ở độ tuổi chó trưởng thành

-- Công dụng 

--  Giúp tim mạch khỏe mạnh.
-- Tăng cường hệ thống miễn dịch, hệ tiêu hóa khỏe mạnh, dễ tiêu hóa giúp hấp thụ chất dinh dưỡng tối ưu.
-- Giúp da và lông óng mượt.
-- Canxin và photpho giúp xương chắc và răng khỏe.
-- Cách Sử Dụng
-- Đổ trực tiếp ra bát cho cún ăn.
-- Bảo quản nơi khô ráo, thoáng mát. Bịt kín miệng túi sau khi lấy hạt ra.
-- Cho cún cưng ăn Hạt Smartheart Adult Roast Beef Flavor  với khẩu phần thích hợp tuỳ theo mức độ hoạt động, cân nặng,… của chú chó (có hướng dẫn chi tiết trên bao bì sản phẩm hoặc tham khảo ý kiến của bác sĩ thú y).', 30000, 0.4, 50, 1, 0),
-- ('Pate Pedigree bịch 80g cho chó con vị gà gan trứng và rau', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Thái Lan

-- Thương hiệu: Pedigree

-- Khối lượng: 80g

-- Nguyên liệu: Thịt gà, cà rốt, gan thịt bò, huyết tương thịt bò, nước thịt, gluten lúa mì, chất xơ…

-- Pate Pedigree bịch 80g cho chó con vị gà gan trứng và rau được sản xuất trên dây chuyền công nghệ hiện đại. Với những nguyên liệu chất lượng cao, gia vị được cân bằng hoàn hảo giúp cún cưng ăn ngon miệng và phát triển khỏe mạnh toàn diện.

-- Công dụng 

-- Cung cấp dinh dưỡng, khoáng chất và vitamin giúp cún cưng phát triển toàn diện.
-- Dễ tiêu hóa, bảo vệ đường ruột.
-- Chứa nhiều Omega 6 và Kẽm được khoa học chứng minh có thể mang đến cún con 1 bộ lông mềm mượt từ bé.
-- Nhanh chóng tiện lợi không tốn nhiều thời gian chuẩn bị, giúp chó ăn ngon miệng.
-- Cách Sử Dụng
-- Cho cún ăn trực tiếp.
-- Bảo quản nơi khô ráo, thoáng mát. Bịt kín miệng túi sau khi sử dụng.', 16000, 0.08, 50, 1, 0),
-- ('Hạt Royal Canin Mini Adult 2kg dành cho chó', 14, 'Thông tin sản phẩm:

-- Xuất xứ: Pháp

-- Thương hiệu: Royal Canin – Thương hiệu thức ăn chứa hàm lượng dinh dưỡng tốt nhất dành cho cún cưng được sản xuất tại Pháp. Với nhiều dòng sản phẩm chuyên biệt với chức năng và chất dinh dưỡng được nghiên cứu đặc biệt. Nhằm đáp ứng nhu cầu sức khỏe và thể chất của những giống chó khác nhau.

-- Khối lượng: 2kg

-- Hạt Royal Canin Mini Adult 2kg được thiết kế để đáp ứng các nhu cầu dinh dưỡng cụ thể của các giống chó kích cỡ nhỏ có cân nặng dưới 10kg khi trưởng thành và có độ tuổi trên 10 tháng.

-- Công dụng 

-- Duy trì trọng lượng lý tưởng.
-- Tăng cường hệ thống miễn dịch, hệ tiêu hóa khỏe mạnh, dễ tiêu hóa giúp hấp thụ chất dinh dưỡng tối ưu.
-- Chứa hàm lượng EPA & DHA lý tưởng để hỗ trợ lớp lông óng mượt và làn da khỏe mạnh.
-- Giảm thiểu quá trình hình thành cao răng nhờ tác động thải loại canxi.
-- Cách Sử Dụng
-- Đổ trực tiếp ra bát cho cún ăn.
-- Bảo quản nơi khô ráo, thoáng mát. Bịt kín miệng túi sau khi lấy hạt ra.
-- Cho cún cưng ăn Hạt Royal Canin Mini Adult với khẩu phần thích hợp tuỳ theo mức độ hoạt động, cân nặng,… của chú chó (có hướng dẫn chi tiết trên bao bì sản phẩm hoặc tham khảo ý kiến của bác sĩ thú y).', 380000, 2, 50, 1, 0);

insert into `order`(user_id, address, phone, status_order)
values
(2, '22 Lê Lợi', '0853123111', 1),
(3, '71 Nguyễn Trãi', '0853123333', 2),
(3, '13 Hai Bà Trưng', '0853123444', 3);

insert into order_detail(order_id, pets_id,count_SP)
values
(1, 1, 1),
(1, 2, 1),
(1, 3, 1),
(2, 4, 2),
(3, 4, 1);

insert into cart(user_id)
values
(2),
(3);

insert into cart_detail(cart_id, pets_id, count_SP)
values
(1, 6, 1),
(2, 7, 2),
(2, 8, 1);


insert into pet_rate(user_id, pets_id, rate, `comment`)
values
(3, 4, 4, 'Đáng yêu');

insert into pet_favorite(user_id, pets_id)
values
(1, 3),
(1, 5),
(2, 3);

-- insert into food_favorite(user_id, food_id)
-- values
-- (1, 3),
-- (1, 5),
-- (2, 3);


