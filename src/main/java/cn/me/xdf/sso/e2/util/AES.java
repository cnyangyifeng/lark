package cn.me.xdf.sso.e2.util;

public class AES {
	static void check() throws Exception {
		String key = "ab23key#iLily*@i!mvpabc123i#!0mn";
		String[][] checkdata = new String[][] {
				{
						"28b4cf522f545dbb:0.412095557ad3592120fb31ccbe8507e2602df395",
						"BuIo49zz99GkSCFxYYqnq65mADoYd0fgricJCqOqAbZEpMeHHBq8u/LwpjNm6pcGXAwNNxCfLk3l+8cwUI1h/g==" },
				{
						"fe5e3a208ecbab4e:0.46209484fdc021ebe5c1342ce1c63c96d667cb96",
						"jAATJ7bxbNtRBbQrb8xTNcTtyr4DpjTKcbVSJyyWlDurKCRm8aiv8DhiD+wfgmnN+/n+8P4OOvyZ3gSaA/FkUA==" },
				{
						"6fdef75a26a95c6e:0.6807415b5b9d29418ea693c1e475d15862eee9c",
						"2v4IlhOuhCv5M2kzdjkaz5xxpb0SuMBNlybLmzEBcMXR+ZBRyxbEXAXeyfNCMKP2Sz/GxhlS0mfpkDeJFJiT/Q==" },
				{
						"45650d8107ac3bd0:0.80778867ebd3e3ebdd79a377c5de6199bd53b90",
						"ycAeMoAxm1u6MZte9Y1QwaUj93OMBEfUszh3qMP1cafuZbd1F+bZrzR9blL610zp3vE/w+OSickFnh0s5GYESg==" },
				{
						"c04d843d295745bb:0.08127308808c2678b8c9b91681ac8bb326a2cede",
						"jL3Viwrq5If3qkPylyhmbmIRg5Af+lzbMxJ/iNawVRV8xd7/9TCWU71JIwzAlv+iTJmsTczwxvk7PkkGNmzr2Q==" },
				{
						"443a9fde9fabb8df:0.9422851b88103c4a65a4662185bfc8ad2724621",
						"56Br+lU7lq+4zv6sPZ3fxO91HUoZp4drZzuo9YX+88fHdXMU8T+ksiutz04+FvdiPQrRtxofRU59e1eIn8nhxg==" },
				{
						"17a580c4220cc15d:0.581728168e853b90014557dd60700b371c3210f7",
						"uVCNzUSNtmHfj1EAvNBRDVAYeYjQg0IND9MUdk8nWtBLlTQceOtqO1hH9cxnsmc2JyFIj/qVV1cHZ9Cs22XOrw==" },
				{
						"bc55b70cec56f86d:0.9149864bb50a2cd8ee8a2e4340fed528f0df1f",
						"JWFJmsTdyJkVovFoNaprmKtzKj7DKyuuWNMHcwhdaRzy8+d2IWpiSlhqS2Pcq51sAtsty144moyjSzAmu1bDwA==" },
				{
						"f00742fb655ef4f9:0.5144630767b843b1fef1da791c288fa5fdd6d54",
						"+nEdk6cEuM7Xa9EUyaKy/YTpt4856/Ln7dgYepVNTKlTAAJ9aR6CPe4V9HRwwIucpu6EfZ0iiYwWgC9xkMoiQQ==" },
				{
						"b1762d957376e7f9:0.869291845d740b7b2532691ffeb9bee1500abe72",
						"228xK+QUsZcV9+lp6uepiapps6ZoAQOy73tbhppfgYavoKNjKa0+mt7M8tcdg+fbg/QgFfzZu/PWaZs9oPooLg==" },
				{
						"6f087aa379c6b9ea:0.85844728e59cd9368e4d993d66801e999733200",
						"8WRGpJDpa4dTre7pWvGFHRDzwXWwrH5QHkFUcCDgBhMoNLdfomsg/YOXnwNTK22tWfLduifsdTAusYUpVrDedQ==" },
				{
						"9892cd1e66b89130:0.1120951842f21742bb71708888afb3d713802250",
						"Cr7eSbyIkwLL2H7A7EviLoL5+2ADTM9KMeIy8DAaeQ6B5aAoxciD8SARWBqNqRdoARkmtNXOrqFEl+PHrIg8RQ==" },
				{
						"ed80afd1b5c86a85:0.060889184354b878f92f240ab44fa5605268f735a",
						"09z4DCql8sUDsBMu42n3L7qo+aG2uPGYf3ZfIbWw7rZ7k2HrAkB+cdXRiB4bk8o/HVdi43FXYKIcF9BFz+LLfg==" },
				{
						"f25ecca3330c0ada:0.80230296cfee76710bfa71259fba1440c5e49ed1",
						"HTjr2ks4wrIj24pCSJMVpzx3jUVO7xOQT+/CrGKvWnnC9dCCVz9BKIsRIeR16+HY9czXDcg4xMr+A6sPNQq4MQ==" },
				{
						"d0363393eb051fc8:0.252948282dab6121184b8e36f020c61eef92c6bd",
						"kXgyLXpMI22x/Piixnxg622RQNo8fU5y522vICiWvLqvWUD9kVseY36Kdwz8PuNgFr2Fd7Yk3Osm4Ht03p7CaQ==" },
				{
						"3749c3a3fce5bae1:0.23963672bb662e1e47386668875d9259404b0272",
						"a/wH9hOCaLib+SyP6z4PEf3xU5IXek+ha85rZi38Z4vGRV/KIC1qtcxuheUxFRP1WyfeC6pyWvbE0Stkz5zCBg==" },
				{
						"a5018099a57c1fab:0.7870552bfed1204e8e1cc72c563e78c6d0d547e",
						"rjX/QNk7+5BYTXUky93amWOixSCe+wmaXSMBFr+xW353IvC7Fxn4ce6SdxdER65qokmdxeLj7IdALdR1UC5UTQ==" },
				{
						"a82790e43a009297:0.69746846aef8f19998a6804bdf83dd2722c89a18",
						"E9RvdLW2/M03ZiuDuEqb9hywcV/k8ii4crslI6mCLyeDeLftt5JtrWQJoImK/qLVIEHCGw7j2F14TYOa5xQbLw==" },
				{
						"aee02c6a08be5192:0.2518726670554547f5b610b1e154c2c252a01ed3",
						"b5GNGmKlbNEyyWT0QFTpjJ9ucgTWI3CjA7kayRJNl9vVm2VeY8MJy0ZiK5uveQXlZoc/Dk2foUJp7vukmaBpxg==" },
				{
						"9a821e4a720d1887:0.8306036283a764193446ffcede19734a39b30b4",
						"rwqO43FjxJ5TBlSum8ni5AGTcWroDIp/3+OiVc7iu8bMfLj7ktmZWwWeNgHL/dbtXa5xMZ6TuQEeu0bf6wKKxg==" },
				{
						"8dce618d02142196:0.3511570767188bb31ebf6b421e8a591dc3ef7175",
						"CIGpHKIhc/iEcsZP5DVVlnzo6ikusJsEfb8lADagrwDwiugF0CERZLs+wczz6k7kW290bGCEv6iuTrqauRFINw==" },
				{
						"e8c2e4ff4861b375:0.918507649b731b31026b2a8279abd807715b559f",
						"6qpCzyTFdcEa6NrlBcWw5SM+GSfBauwaRpX0wIdW4Ds8Ksi1ZvL9Fw2wMFXLBzEF8zGRIAB/lthpzFg6CIL2iA==" },
				{
						"1e0b79a3d3501c93:0.8551119676a1c45a16e7446aee78342c0bc1a23",
						"Adv9BR1lqRJDKL+ALLcqgqHtVDwDDwiVJpnr5o/9PU9uhqagIkezf582AkSpZGANVGtxwztSAf3v4RgIbxCw7w==" },
				{
						"c5eebe28dcbd05c:0.849439868c86e51ec9347157ded979d293c9235",
						"wVh4ZNym7ZY/+o0pgx9ajiCxlF4xmdTYNT01YJKeNCgjXv7PRkkHJyWMG3/NOkRPmUhx1dIpi0riaI4xG3LeJg==" },
				{
						"587a426f7d910e43:0.279873240bb1ac300459a1620e44ccef16f5c54",
						"z6fio2bPYoj8TfyHsxj+TOAW8KyVwqJojvNcQycXLYqQq8a9HPH6pbGi1l9n2vK20HciMU8OMqfBnhEG21Lx7Q==" },
				{
						"6c367fed031fb984:0.62549057fe74eb7ec7fb8266b8b23a1cce1926b",
						"wq/Cxrwwu7TqdFuHIOTlmA15lmBlqr3LwhsP6Q00WA4amROqP6v/e5TmyRH7Gxd/RKx5hwoYqrnyarBX4gV3yA==" },
				{
						"6123d32d271057d2:0.91148305c511eb7c433714016a1973626d831aaa",
						"dwXffRvQgKU42ZGUyCD5WnlDD7FofuH+4GyMeMI3nUU74jKivcO9MhJoGhfqMEgAPhqr/bjenzwBvcXztgjV+w==" },
				{
						"c9566914b094570e:0.6204537a0cf384e68b9154b9edd041ae6c4ad4b",
						"FKs74kARoBgyaGZr4/ntQlVJUdirBFM9T04v3WL8taPSP93ypMBzH73/O6YKutwQK+9KHYtEuwngrjvr2f1U3w==" },
				{
						"2d9227df846ae0a9:0.5822323e90d8e5b2d4708cd045ce2073557f11",
						"1haxSOPrKa+t1rqFMkf+6q/HKWSt1L2kMyl/iLen6WFzP4LAo+ebuIfio6C/DV1YLTCr2CbbWdcpWzCazo75UA==" },
				{
						"64aeafcc2588297f:0.6394259a36ab7349737d188795d11a949fb0199",
						"OTnU/8OX/5R0oVZANDtbsJRtk3KgfjZjskmOd22O959XDGOV0K5lG+FO0FbVGXXmXpjF8fB7HOcLeDXELlOKSQ==" },
				{
						"59f10e5415ba3c63:0.739622065c564a940b2c307b359ef03cc5400e93",
						"UtCeOdfqYfqdYns910mmftcsRmvsS4ASxyoYn6exdoi9y5V3Vd9Tmv3HwJ+SpXAL5AY/xYu34ZRBUurR41fpPg==" },
				{
						"d1d276439c82ad39:0.23375571e067a96e55dadcababee01c901de5012",
						"NLysZQPa8MXFL0J3jzTOOuSx7o+z6nPgRZjvK+6HzNlaAbHE4LLp7HtGcgpav8EBYlme7kbw8lfPKfeYeqjYlg==" },
				{
						"a484dcfd8b99b828:0.360818925008d6aa208d50454f06a42ff4ada03b",
						"BXa55x0ckhtHr5DCIU2QgDBHrUyL/0wVw8z6d6WrSL4/IcqG7ZZlkHvb42MiZ953fNFBxfLZvFrxZelbwoGeYg==" },
				{
						"30ebc5e4cbff89e1:0.85476416ac2be3a97a701fb9ab06e59147f83005",
						"eDFck7V7ZdxXccf8yleo3i5Dw7ZpoSe+X3EOriLy8ame/ezab97+emJWIxQdPPDQ2IVLSx9WDhY0XrJ3Nwq39A==" },
				{
						"c678fe0cee60c6ea:0.6080759dfdbddee8e4a17d196fbb69ccc02ae9f",
						"4cE3XJj0v7Qu2byxV4912EuskPsIyC169iFs9V9THfcaSgBRtw7cRdCYiEUgZt+eKGtRSFFTYTaRHm/RfMx9xw==" },
				{
						"a77fccc2666b8b22:0.162698697172923fc93c539f7fce15d31b8ffc36",
						"PbE2I3mQOaEAptOqZ9LUpVlWrER3rw5Qbb5c+o1i/FnJnnElImj6+u0ZsJ3NFVTZJioAk2Uu8FyDbEbs/5uhyA==" },
				{
						"fd88213fad2e0731:0.39493972df02f7ff025f947db6ff3f22765701f6",
						"fTB7HoqbHOGyZS/OjGYr4h4uJEFNF6hFIlQyOfDUKIpo/TFHfkiD5krmwOmRaiOjgL6KO1RHBedz8zRohzd5Tw==" },
				{
						"35c5aff886d3f04a:0.5185545743d459d23a3eae9abe585bb6e5873540",
						"FkG+aj8p0coEVUcLtrxkycrrbvipXDSGx97fr/cWItY+TJa+/XtRAm+xvZ4nrfimBKrkbgBBaCHHSu9pSJSdww==" },
				{
						"fd3df5675f97ae72:0.505754957ad388518a4fd3e42de0fa7cd6e74cb8",
						"hzLPSHvd/Y6uhiWIrE/Ol4kxbW4msWo7ngE73jc0Wu8MiGmh1oFNJ3jr9ZjzHoCfgy5p58YDT2b5zRs95MDo1A==" },
				{
						"74511f366ce236b6:0.48281854b1d95afe260d201ef1b5df2abf38e401",
						"bIHrLoRJELpDHwu3Qw8Y/bKQwS5Z4BiAoykD5SkQ0KWdQoj90ux8Y48/j/prPHb1r9niJA0y316QzokYNpHNjA==" },
				{
						"5db92e906476d535:0.0510865454294360b00c5dde7d319acce441f173b",
						"28XDM961DyliNANmdpK6Gos1/Dm2hMIg7X9XHEtZm9TEg/DiQSbuzCm13e+Aotu6XEoqrx1cDH3nhU8zoK4mCQ==" },
				{
						"54af0ad213c5af12:0.3514790551dfe3226d991c228c5c14b5253611b2",
						"C0mCT14V2JUevKNcsqw0QZAW8fU92Y8I+fAVpwOEH7L4vD8WHU5fCBWNkM3FUbkVcPkiYQk0rB4ZPiK6QxuFMA==" },
				{
						"91ec684dc919bc6e:0.93096874224ffd2483c9ad490f5b3dd366eced1",
						"9sYrruj0eFKzJetYW/0OMtalajIxoMQzjMiWh3vJdCRmsXVnxmelwIEH+e4o4TOGZwUbpGVlz7iIKXcZi2vpcw==" },
				{
						"a826914b689affc4:0.8908287b4d503340809e16b65d9a8c0d9184a9",
						"WSSTWzyLiMZf27HYbSUkBuda+gG4vYsF5Wht0ZwJl78jG05IjtiEv27X4we27ZvKgfp2+FIKqWcHVOZ0mbGSAg==" },
				{
						"4f0eb3a803c7ffd0:0.785000561c63122aca8fd6cc2b3b40ad69ac0b4e",
						"Rydmw6CzejE3Y7ChfmORiR/ymYUvOKjy+/2Q+ucIKj+wTQfrbwwpDk8qVISRn0Ezt0DSamwQDONfByG6VpZaiA==" },
				{
						"d79a3a077a03bf2b:0.6118892b4c69001376fe0f063341046ef8e80c",
						"gwwBjYGiAD1qQ2rTghwKDtxHtM8MJKAkMk8yjb48hAGQuq+7sD6YvHmeITdCuOpUEo0U/moEaDzxZST5exn2kg==" },
				{
						"aaa866841294920d:0.21206367b037b1fd4e687db86ca5ecc6eff1b202",
						"C6cQrSBdQW3ytJ+0QyJwLlOQaSYXKCZ6Xz8mSExl50L8ExE2cCoMlCrcUyk8MSVf5WAMoOTM7FdsPQS62nF6LQ==" },
				{
						"6961329ad5369e77:0.67255567ee23b8f66c69d17aede6cb3329a29d4",
						"OrbeZASWbwyIVIEgk44kRrpg+IqxtjDZDlpQ/8NnbrNsn8w/8XxB6qCEDstnv1oBFtHZOp57iGqxXIf0k1P1dQ==" },
				{
						"ee269eaf28f1a737:0.63316095305f464ad48c8b65e7fedaffc94e9781",
						"3qTgD2h8LaC7SVs3HQmF5yiDjAFdao+EBBSqn28NzP0RdVrZhtgsqiQFIDPPGS2iYNZ7owl0ND9C/u2idR27bA==" },
				{
						"bfa8510eef15c23d:0.03074354c48cad400ad73e12a03828d43abd7df1",
						"n4Z5U7EvivjRXfGKFj9p3AOt9aw7MAa9ECL1BxB9QVZgBlRetaU0zTtUbge83c31Aj3ScJf0wuDXSQXoINA2sQ==" },
				{
						"ed05ecd995af26c3:0.052431226c70e234c1d7711a14a971ca0464131e1",
						"BbFtpGFEFacQrHDbm3K7wRfQgqAtmyNCj4vcBxhUhBOIOf6SGA5py/qF8djF4UY+aK7lelIVxm/3tqMVz5LyJQ==" },
				{
						"9bad68dab964f018:0.14695841d2d2506a572c142eb5aaed8c64ae66e",
						"eLnRE+DFgMtErAdPes3oyzvdnAX6AdlDHNuIUH8zyiwkRIsiASIwc/bcp/xkAaLUoRG5lVsI1bHjeLLVEB5VRA==" },
				{
						"72ca541501a531df:0.63025147b4d4fe682c7db2766f56fb400d20e329",
						"YOHMiR0KkTgYToRbDFhbl3xvUou4RW4Z2OVGkAjb38pWKqvdL50Z/YOFhEYwhwdL+zW4EDWf4r7ZYYamYyuQZQ==" },
				{
						"30dad98a1fc5b8fd:0.72630056e3e27df337b9c15ffff6c28a85cafe6",
						"zUZtCm9Bq9LUOruIaKUpxcrN/eCZL+0IaNe97u2AUVXeZCYTv56In9b+Uixgzejp3fxRX/HaClNo1xPfz+MNEA==" },
				{
						"a38d48b447198c15:0.5839692461e335c58c5d0ed0a06cdcb196adf024",
						"TGrHKxgTYI6KSgGFPk6N74lLw9YwDbuFPQTm60xfS5CQTO5Z1pGBUjHi3oBvUTDlxuJfRkslqdaDUhR+BySv+g==" },
				{
						"c23e75491babce03:0.75255362eb85832068373724d49b975ec4dceae",
						"ZGwuNaTts3R5PrRrG68nQILHCQ7ml4TS4a+HZL1iHnQVtAxxApX+4BMdZ5W2nxNgskgZD2DCq8QIOCHwk+sgpg==" },
				{
						"ed702dacc99b9371:0.94814117652763f9ac822dddfa09cab6e269df",
						"kWbK+Ld+tRONxUtP2D8MHxdXhWPXgPUPROlCqDjeTy/8GK+Qsj1BdJ+7kmX6C6OVlwJya2OF2xgDqgJ7DMDqKw==" },
				{
						"adec2cccecfc9cf:0.067761352491b008a10674f67881737e22dc478",
						"ll76k4y4yucBXqLd3txSZeMVUPXrfb8echQr3UGi0Ts4wj5R6m3/yZTlrvVrvzxtAXesrx1//a+pQsNCBqrUJQ==" },
				{
						"e7bf09be06b9c2a6:0.59192772c6002f6f9a82233f7e281f71c2bb875",
						"6GQ6NMq2ManQ3mzLVUXKz6QMaI7fkB7NB6fWnHv/WZsrWjYrz2xi0ZfiXXAQv407Kus4oB1YiwpFLj7tvVwFuw==" },
				{
						"8ed081529438fae8:0.47255462fce209e0f4c88caac7c1e932544d7f63",
						"D+UtOwAUR7ph9+YeXKW6N4YpWVO7kpOZkGXTCBh2c+h5CY6DW3Hi1B2CmVhtQosPLokzzieYdfaPfCaajT3D8Q==" },
				{
						"98495e0a2893c2ec:0.6305613bb819f0ade04ed3a76b284cd9f0723f9",
						"NlWUoX4awYjID/EX+1hR8JLddm0THVmkVu0i84wjg3HC+eUe4hLpmSnewhEjtfjY6e0pJiYm9LCBHS7Dae+m3g==" },
				{
						"7b3006ace82eb39d:0.243851846dd5842c89e5b5695dbee702545ecaae",
						"dofO+hg0t8dtCiAee5OLr9Q7HclF2YxX84mxb3+OxeRBgkp7Zy3BE+6y2jlQKqLM5RuTfgTi9CwMbK0zKTDyQg==" },
				{
						"4b193de91f1aac81:0.725807489143510e96a6d278a236828fe663c97",
						"ib07IfGXVjgda3FxjagcL6itrJrfmez0T97EHCWbnq4kDBCyVB0ayKmCX/v1iUqbpWQyRO13hxLiZnmOPTJ5/g==" },
				{
						"a04dde89863c6245:0.392586773cdb39fde82528c03955da2d63af3e85",
						"4hLhiGXpaZzEc7wZVhkcXTq0G11VLWuFKTbqlmqdnMurn5vu8cJ4viCi2kZL0BX3svIXTqk1CXB0TgNUYn0YwQ==" },
				{
						"3dcf941c396f8c19:0.229768047ded5162f8046430ff8ffc6f45b26d49",
						"LHrv1VQMoSlhA7Jdf65WRY2IsoREMyo2Q4OntHZEZKilE1Kj8qBl0iuJRdHutstfKxVdw5bLwDF8QgIKneHOFg==" },
				{
						"a08acf8e3b87c8f8:0.4245208a35e59c7469a40314ea95a2ee3406faf",
						"XJU1Jrla30QigaxIsMVwVNMeIUwi06tVtq90fgm5TBtyOyCrWT430S9Tc7DrhfaKewKa6V5pdqJRk7uqyKkNqw==" },
				{
						"39e9f3a9915f68a6:0.2803316fe5a0b61f9d96f6969fcfa07ca92e4b8",
						"7WeixyrO+XtPEsNPf74wZJUIpxncVg4GakpDiT0KNMM0Ofv3F+4AuqloAmAz+nSflLMVpfwSa22lXEymsU67Rw==" },
				{
						"522765c7b29a2621:0.8235934d5b8ada6c54f4c49fd501571202940fa",
						"8rIvcIMyztUURc7fDDgsWtKwBxhyd0p4xZXe0hdWiFcnQLGkvGJaPmfwCqQBeTO2OSaC3u1jHG2efSCCY2ttjw==" },
				{
						"5d85fb0b3d102835:0.055190623dfad8f39304d771db0c50a3bb3146615",
						"3tZLsmkHYX0CuFQCudRyCKnmVSs02YL1EmUmC4T7T3FEpPtLxVt3ihNNTGbgk1fHAX4M0fOqOA9/cWULNOgkLQ==" },
				{
						"e8202babb6c18f6e:0.72234317327fcf5034819f7c596a2e692af7b5",
						"STC1KuTtAIhbNBuTJOmTgXCQLhOgacRmb4SUOB3aO82c79wPEtp1brrghDKjDjTEawDF7+6oOxCUJa3dOheelA==" },
				{
						"6f013e72f266a3d8:0.4715792577a75f60b1a575e542c2eb07e01016",
						"HBAb+EgS/64zL7W7kZ6C5c/G84nQue23rOgdBaYjCftWkimBBdhiEq4p0ri+bl41cBTFy3xBz1R513u8x1AH9A==" },
				{
						"bb8f219e5c92370b:0.123004293a01d8c148349c5cf8aacb481cae1a6",
						"HbsYjUq3F+Tlj11ulZsUhcg056Pp0p0RpWAT7RthWX7ewibIwroOZ0ItuJ820Vwp0joHpDeMtl/QIISLSyibug==" },
				{
						"a1fd7253a65b49da:0.471956978f1329ea4991f365a0f9afd9061aca58",
						"SjHKuXxVynMf8vvpA2akkQkHI/sDOyE4iSfP3ibG7otrTQaQymKxV680JaWhvKY+eqh6NVuppXuw6WN5wTQlLw==" },
				{
						"239199a8bb745f2c:0.177537022b6256d9be0efd266973088b58c54632",
						"dOD4RazmFQDTggSj6h+9ATIVJlfF6UDDpEwR+jHgUE3yBGTRDNgTS3Gb9OEok9Vr2u+f9z4R82q0liZa2RTEvQ==" },
				{
						"8dd73972c0beaaa7:0.3596304712fcf48e731bf4861ac6158b1dd261d6",
						"GKdjFivmbivdWBBv4usMsvyjpuN0l9mEquVgHT82vegdUedcQQ7EQ1var+wbotR3HLq+yOtrB2pG1dxUIBhBLQ==" },
				{
						"3b0fb001ed857253:0.42678828d4ff4873160120ce90e8053c7fdd5b6",
						"RRg0DGSSNbqzeC/21CAtUfeY73JYbyWG8LMQDV72co+5dHpLFWwa3mTTVQ6CwBRfddiumgkIhaLnyc7ow4qKPw==" },
				{
						"45641a241044882f:0.07612777d047206eb70159e57a40c8e5b2dc7b38",
						"HbuAGOTDnJMTmGQt1bj2V7arOWD4cKn4AwviQoQrY2rXjiMdKxwc9syYZKha0n164lxtX9Z2aJSIfvgJkB9vYg==" },
				{
						"8ffa58aae49ed5:0.14131373d238e14f7f31023e8ed0bc908d862142",
						"owyNZWunAFIXoKN5kpp1TPj7B20nZdNHs8CyVzhpTAZZCP7n47AYCtwG6ddSPUimgyB6Hz71e1ozFH4jKehpIg==" },
				{
						"dbb8c48b2ecc48ba:0.603378061dd224c18300c83c48d8140ca7ecf621",
						"Q4qnsApRAjbaVEykRY8JUKwKAa71cLlQjROj9tgK0PD7QQ/G3ww+v0XcQPqp6shgpIdt1KUk3Iu6zWeK0wuONA==" },
				{
						"72b2073771868287:0.3279045242d03a7a9a0cdbf942502b8e3904aa2f",
						"LzGPAKaDRcoabH0rboWB+M3tdDGoTyPBAN+U/OnCT5h4tm4g/+0S13l+ye9QxuJyeT4HPZnQ1aiMm74rc1WX8A==" },
				{
						"643c8ff23e165552:0.19478762cd57e9ad08c1c94620f27c304bd936c9",
						"WENjI843QktV8K19V2VFpYivaycZVckQlo/qAEHG0xUis6czuF2zRsYDyQD/AujNBhBr/tYnUHLjaCTVOHh4PA==" },
				{
						"615fb3cb04d277a2:0.3230084888f5bae3de26743073f4fd2129831c51",
						"5GLvFhT0zze/98ZJ+OYMnAmppgXdEhcILuBIm60Twiy2CPB/rZ30vDfJ7O6+fMqJyN66jcjCxIb9vNGWIcEdNg==" },
				{
						"1647f7922253c15f:0.857738780b72b7322b9f0b0a5ae254da3e99b82",
						"kLwQSbUWRKAc13H4CH1XaZgav8e9/pY8WWFFTxBz/s832FiL8kkO9NYgiD5kazPvKs2GWuH1VCEraqEommukcg==" },
				{
						"224689fcf126fe32:0.76404352c47876f781fcf565486d2ff53c416b3",
						"09lO7GzhUfN374pfseYREV8A8ZT5soVOTubqvlTQ8fxCey2zu1tI994nO+CB7UAZfXcfHA9lM/e/VhxEC6X3+w==" },
				{
						"4c897760e0c0ec04:0.06847465a961a7fd1f6db89fe5acd81a34553a01",
						"nIieXKYpcUx5QmYNsZ91Sz+TZ6hDR8z0UvBR/lMd9U43Qx9YmNW+UEQO38sNcFjszrXEHFYNVhT56Q922FCg9g==" },
				{
						"7db5119a11e31dbc:0.42543342c4f5e59586e632245b222d31847d60f",
						"sFA1GkH8E4WxG5uKQRuxOfrSP7HYVfYB+TiOboDNMe4dvFmsQgiW2Tb/mcrwbhbP7CPPo2HRAggW9U1zKW1F8w==" },
				{
						"485387ca6fac6d56:0.7299434d90b6574f74dea28c86e23738eca6c7d",
						"SUluKaAjy81xNqPK1nL7bBWVSF5FauaxmicHvrOzNAkD76HEP3znchG/ST4+eG6w1Zv8UAMKGucfrGG/7zN0KQ==" },
				{
						"3bb596fa582f80d3:0.217410861af71aebb9c09f092c7b515f3979f194",
						"lIe7ipaU88a7fJXvPbC77xmEo3phtX2gI69Ef1FQXhc/TfV6szY1IGm0l+dXZypuWPDd/ccwCCaEzF+2NxJAkg==" },
				{
						"43062b550cece094:0.060832262f054d1f6c3611e50dce7064af502c8fd",
						"kI/nRqb24/x0bkUbLII/f0bg7yhJe20g+dy7j7cmb/JevJlGbVD0C1SaZkoEqiasWzuXcloFvIL75FzdACqi9A==" },
				{
						"de992e7282fc08d7:0.4203183cf93757483698264840b0e4c11c37592",
						"VcXUv+nqOWIDY41cnb3H/Sdom3lKycjpipfv58lfUOrt3llSxzym9S128WwU4CnUJyqP6GFRZryCm6fV3qxP+A==" },
				{
						"f3fa9ba3348cc9db:0.05088746553990b7b431c6273b460cfb2856e6571",
						"5zYkE/MVOKkgaeACXkdwtwO4oOg8+ff1Jol2c5MPG1DCsaSpe3KmQW7WZ/yVUBbinusByS7crrJiTXie7gGyeg==" },
				{
						"2fb6ff6882ebba19:0.786541468c85739498713e7d88c7e78c5a5d11c",
						"Jmw1Mub7BRaBY6xuH+6EwqRQ5E6Jth9xb75SlW3rnGcdaGHLZVhMp/pTLDvOCAUZdu9MBbZj50wHvE0ZoJ9bjw==" },
				{
						"4c2db3550c32410e:0.080243778e541c83830cdf1b341ac2990911d973",
						"mddfmZlJvQ6VBLfEjJ47oOdXCxKCpwdhC0wpfWCc32R6/VhJLsN911LCeSCM7dtHS67VhojQtkohuDffI6obYA==" },
				{
						"7390f2e747d8b910:0.733650451c452f3e8b8ec7faf002dee0199992ca",
						"CVG5qrXwU9mCo83kdW5QekjPeHFpf9y6Lb3S0bMA1Rev4CEm6GIYFJla7YqdJ8lZ68zVD8UWTLgdDlsVg2NcOg==" },
				{
						"e480c8b151ce04f3:0.650018044dc4b5444b829aaff10e575e374ed2b1",
						"p9Lz4iTP4DVcUlmG59Ado+gJ7q9e2j+oScx/nThlkUovc/hkjfvqb1IFJaa15mJRcgS5IwHKDWE6lfIYulIFWw==" },
				{
						"433711022ea9af67:0.8164916663f03052c021e546fbf8c6f0f14a61c",
						"lO3btmWPv9gG+TyMwUNsofwGEsy30d1rRSWaYTGfdSdIfF3Q1DuzgS/yFaQXRzMxYVAFujy/wFaoL1lo8Ym4VA==" },
				{
						"6c155922236470a6:0.0993551653e3b22548cfa1856dcb0135bee05b78",
						"5VnpG0W8qQT9eiUfBXlDV1elQmewWKvQ1WYfh00uCVi3BG7kBMifzpGLr0JukL+V6mvczLA3G93tKnyhrltnzQ==" },
				{
						"b454f57410afe40a:0.71313398d1fd87ec3d5c16b38225568b58ef62c",
						"xvWFMtO+QsWCG46mUnTzfeCfn7tGtLlYE2L75kFzc3bwzff4uhMt55Lp2XWtt9qshUFjtO1Eu2g0GitkxwjS4w==" },
				{
						"e96037e8ce0352f9:0.5676633376e8039ed2133d719f62ee9e3b2df",
						"H5JnvR+o7ELV9qx2KFyjlrGY8xQPJ6qYE4EEN4fBn7KwWpSmwisCgs+yq6nvo++3yAAIt63q8s520xyQv6jd+Q==" },
				{
						"a6431f2adc3dab84:0.91788193b10d9edbebe9c4dcac9b72c92f9dbb4",
						"87McFnht+UHjhF3C6ieuiN79Rhk0yTZMWGR4QWiKwFQYiA+UR7leZVeRDnMJcNBA7mkZ3zmRkWLPb5eNlan6vw==" },

		};

		for (String[] arr : checkdata) {
			if (AES.encode(arr[0], key).equals(arr[1])) {
				// org.noe.framework.util.log.Log.debug("ok:" + arr[0]);
			} else {
				System.err.println(arr[1]);
				System.exit(0);
			}
		}
		// / org.noe.framework.util.log.Log
		// .debug("与php AES [www.phpaes.com] 结果对比  对比测试通过！");

	}

	static void check2() throws Exception {
		final String key = "ab23key#iLily*@i!mvpabc123i#!0mn";

		String msg = "";
		for (int i = 0; i < 100; i = i + 1) {
			msg = msg + "" + Math.random();
			String checkdata0 = AES.encode(msg, key);
			if (AES.decode(checkdata0, key).equals(msg) == false) {

				System.err.println("##" + AES.decode(checkdata0, key) + "##");

				// org.noe.framework.util.log.Log.debug("加密解密不对称！");

				System.exit(0);
			}
		}
		System.out.println("\n\n加密解密对称性测试通过！");
		// org.noe.framework.util.log.Log.debug("=============");
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		check();
		check2();

	}

	private static final int BLOCK_SIZE = 16;

	private static final int m1 = 0x80808080;

	private static final int m2 = 0x7f7f7f7f;

	private static final int m3 = 0x0000001b;

	// vector used in calculating key schedule (powers of x in GF(256))
	private static final int[] rcon = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
			0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e,
			0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
			0xc5, 0x91 };

	// The S box
	private static final byte[] S = { (byte) 99, (byte) 124, (byte) 119,
			(byte) 123, (byte) 242, (byte) 107, (byte) 111, (byte) 197,
			(byte) 48, (byte) 1, (byte) 103, (byte) 43, (byte) 254, (byte) 215,
			(byte) 171, (byte) 118, (byte) 202, (byte) 130, (byte) 201,
			(byte) 125, (byte) 250, (byte) 89, (byte) 71, (byte) 240,
			(byte) 173, (byte) 212, (byte) 162, (byte) 175, (byte) 156,
			(byte) 164, (byte) 114, (byte) 192, (byte) 183, (byte) 253,
			(byte) 147, (byte) 38, (byte) 54, (byte) 63, (byte) 247,
			(byte) 204, (byte) 52, (byte) 165, (byte) 229, (byte) 241,
			(byte) 113, (byte) 216, (byte) 49, (byte) 21, (byte) 4, (byte) 199,
			(byte) 35, (byte) 195, (byte) 24, (byte) 150, (byte) 5, (byte) 154,
			(byte) 7, (byte) 18, (byte) 128, (byte) 226, (byte) 235, (byte) 39,
			(byte) 178, (byte) 117, (byte) 9, (byte) 131, (byte) 44, (byte) 26,
			(byte) 27, (byte) 110, (byte) 90, (byte) 160, (byte) 82, (byte) 59,
			(byte) 214, (byte) 179, (byte) 41, (byte) 227, (byte) 47,
			(byte) 132, (byte) 83, (byte) 209, (byte) 0, (byte) 237, (byte) 32,
			(byte) 252, (byte) 177, (byte) 91, (byte) 106, (byte) 203,
			(byte) 190, (byte) 57, (byte) 74, (byte) 76, (byte) 88, (byte) 207,
			(byte) 208, (byte) 239, (byte) 170, (byte) 251, (byte) 67,
			(byte) 77, (byte) 51, (byte) 133, (byte) 69, (byte) 249, (byte) 2,
			(byte) 127, (byte) 80, (byte) 60, (byte) 159, (byte) 168,
			(byte) 81, (byte) 163, (byte) 64, (byte) 143, (byte) 146,
			(byte) 157, (byte) 56, (byte) 245, (byte) 188, (byte) 182,
			(byte) 218, (byte) 33, (byte) 16, (byte) 255, (byte) 243,
			(byte) 210, (byte) 205, (byte) 12, (byte) 19, (byte) 236,
			(byte) 95, (byte) 151, (byte) 68, (byte) 23, (byte) 196,
			(byte) 167, (byte) 126, (byte) 61, (byte) 100, (byte) 93,
			(byte) 25, (byte) 115, (byte) 96, (byte) 129, (byte) 79,
			(byte) 220, (byte) 34, (byte) 42, (byte) 144, (byte) 136,
			(byte) 70, (byte) 238, (byte) 184, (byte) 20, (byte) 222,
			(byte) 94, (byte) 11, (byte) 219, (byte) 224, (byte) 50, (byte) 58,
			(byte) 10, (byte) 73, (byte) 6, (byte) 36, (byte) 92, (byte) 194,
			(byte) 211, (byte) 172, (byte) 98, (byte) 145, (byte) 149,
			(byte) 228, (byte) 121, (byte) 231, (byte) 200, (byte) 55,
			(byte) 109, (byte) 141, (byte) 213, (byte) 78, (byte) 169,
			(byte) 108, (byte) 86, (byte) 244, (byte) 234, (byte) 101,
			(byte) 122, (byte) 174, (byte) 8, (byte) 186, (byte) 120,
			(byte) 37, (byte) 46, (byte) 28, (byte) 166, (byte) 180,
			(byte) 198, (byte) 232, (byte) 221, (byte) 116, (byte) 31,
			(byte) 75, (byte) 189, (byte) 139, (byte) 138, (byte) 112,
			(byte) 62, (byte) 181, (byte) 102, (byte) 72, (byte) 3, (byte) 246,
			(byte) 14, (byte) 97, (byte) 53, (byte) 87, (byte) 185, (byte) 134,
			(byte) 193, (byte) 29, (byte) 158, (byte) 225, (byte) 248,
			(byte) 152, (byte) 17, (byte) 105, (byte) 217, (byte) 142,
			(byte) 148, (byte) 155, (byte) 30, (byte) 135, (byte) 233,
			(byte) 206, (byte) 85, (byte) 40, (byte) 223, (byte) 140,
			(byte) 161, (byte) 137, (byte) 13, (byte) 191, (byte) 230,
			(byte) 66, (byte) 104, (byte) 65, (byte) 153, (byte) 45, (byte) 15,
			(byte) 176, (byte) 84, (byte) 187, (byte) 22, };

	// The inverse S-box
	private static final byte[] Si = { (byte) 82, (byte) 9, (byte) 106,
			(byte) 213, (byte) 48, (byte) 54, (byte) 165, (byte) 56,
			(byte) 191, (byte) 64, (byte) 163, (byte) 158, (byte) 129,
			(byte) 243, (byte) 215, (byte) 251, (byte) 124, (byte) 227,
			(byte) 57, (byte) 130, (byte) 155, (byte) 47, (byte) 255,
			(byte) 135, (byte) 52, (byte) 142, (byte) 67, (byte) 68,
			(byte) 196, (byte) 222, (byte) 233, (byte) 203, (byte) 84,
			(byte) 123, (byte) 148, (byte) 50, (byte) 166, (byte) 194,
			(byte) 35, (byte) 61, (byte) 238, (byte) 76, (byte) 149, (byte) 11,
			(byte) 66, (byte) 250, (byte) 195, (byte) 78, (byte) 8, (byte) 46,
			(byte) 161, (byte) 102, (byte) 40, (byte) 217, (byte) 36,
			(byte) 178, (byte) 118, (byte) 91, (byte) 162, (byte) 73,
			(byte) 109, (byte) 139, (byte) 209, (byte) 37, (byte) 114,
			(byte) 248, (byte) 246, (byte) 100, (byte) 134, (byte) 104,
			(byte) 152, (byte) 22, (byte) 212, (byte) 164, (byte) 92,
			(byte) 204, (byte) 93, (byte) 101, (byte) 182, (byte) 146,
			(byte) 108, (byte) 112, (byte) 72, (byte) 80, (byte) 253,
			(byte) 237, (byte) 185, (byte) 218, (byte) 94, (byte) 21,
			(byte) 70, (byte) 87, (byte) 167, (byte) 141, (byte) 157,
			(byte) 132, (byte) 144, (byte) 216, (byte) 171, (byte) 0,
			(byte) 140, (byte) 188, (byte) 211, (byte) 10, (byte) 247,
			(byte) 228, (byte) 88, (byte) 5, (byte) 184, (byte) 179, (byte) 69,
			(byte) 6, (byte) 208, (byte) 44, (byte) 30, (byte) 143, (byte) 202,
			(byte) 63, (byte) 15, (byte) 2, (byte) 193, (byte) 175, (byte) 189,
			(byte) 3, (byte) 1, (byte) 19, (byte) 138, (byte) 107, (byte) 58,
			(byte) 145, (byte) 17, (byte) 65, (byte) 79, (byte) 103,
			(byte) 220, (byte) 234, (byte) 151, (byte) 242, (byte) 207,
			(byte) 206, (byte) 240, (byte) 180, (byte) 230, (byte) 115,
			(byte) 150, (byte) 172, (byte) 116, (byte) 34, (byte) 231,
			(byte) 173, (byte) 53, (byte) 133, (byte) 226, (byte) 249,
			(byte) 55, (byte) 232, (byte) 28, (byte) 117, (byte) 223,
			(byte) 110, (byte) 71, (byte) 241, (byte) 26, (byte) 113,
			(byte) 29, (byte) 41, (byte) 197, (byte) 137, (byte) 111,
			(byte) 183, (byte) 98, (byte) 14, (byte) 170, (byte) 24,
			(byte) 190, (byte) 27, (byte) 252, (byte) 86, (byte) 62, (byte) 75,
			(byte) 198, (byte) 210, (byte) 121, (byte) 32, (byte) 154,
			(byte) 219, (byte) 192, (byte) 254, (byte) 120, (byte) 205,
			(byte) 90, (byte) 244, (byte) 31, (byte) 221, (byte) 168,
			(byte) 51, (byte) 136, (byte) 7, (byte) 199, (byte) 49, (byte) 177,
			(byte) 18, (byte) 16, (byte) 89, (byte) 39, (byte) 128, (byte) 236,
			(byte) 95, (byte) 96, (byte) 81, (byte) 127, (byte) 169, (byte) 25,
			(byte) 181, (byte) 74, (byte) 13, (byte) 45, (byte) 229,
			(byte) 122, (byte) 159, (byte) 147, (byte) 201, (byte) 156,
			(byte) 239, (byte) 160, (byte) 224, (byte) 59, (byte) 77,
			(byte) 174, (byte) 42, (byte) 245, (byte) 176, (byte) 200,
			(byte) 235, (byte) 187, (byte) 60, (byte) 131, (byte) 83,
			(byte) 153, (byte) 97, (byte) 23, (byte) 43, (byte) 4, (byte) 126,
			(byte) 186, (byte) 119, (byte) 214, (byte) 38, (byte) 225,
			(byte) 105, (byte) 20, (byte) 99, (byte) 85, (byte) 33, (byte) 12,
			(byte) 125, };

	// precomputation tables of calculations for rounds
	private static final int[] T0 = { 0xa56363c6, 0x847c7cf8, 0x997777ee,
			0x8d7b7bf6, 0x0df2f2ff, 0xbd6b6bd6, 0xb16f6fde, 0x54c5c591,
			0x50303060, 0x03010102, 0xa96767ce, 0x7d2b2b56, 0x19fefee7,
			0x62d7d7b5, 0xe6abab4d, 0x9a7676ec, 0x45caca8f, 0x9d82821f,
			0x40c9c989, 0x877d7dfa, 0x15fafaef, 0xeb5959b2, 0xc947478e,
			0x0bf0f0fb, 0xecadad41, 0x67d4d4b3, 0xfda2a25f, 0xeaafaf45,
			0xbf9c9c23, 0xf7a4a453, 0x967272e4, 0x5bc0c09b, 0xc2b7b775,
			0x1cfdfde1, 0xae93933d, 0x6a26264c, 0x5a36366c, 0x413f3f7e,
			0x02f7f7f5, 0x4fcccc83, 0x5c343468, 0xf4a5a551, 0x34e5e5d1,
			0x08f1f1f9, 0x937171e2, 0x73d8d8ab, 0x53313162, 0x3f15152a,
			0x0c040408, 0x52c7c795, 0x65232346, 0x5ec3c39d, 0x28181830,
			0xa1969637, 0x0f05050a, 0xb59a9a2f, 0x0907070e, 0x36121224,
			0x9b80801b, 0x3de2e2df, 0x26ebebcd, 0x6927274e, 0xcdb2b27f,
			0x9f7575ea, 0x1b090912, 0x9e83831d, 0x742c2c58, 0x2e1a1a34,
			0x2d1b1b36, 0xb26e6edc, 0xee5a5ab4, 0xfba0a05b, 0xf65252a4,
			0x4d3b3b76, 0x61d6d6b7, 0xceb3b37d, 0x7b292952, 0x3ee3e3dd,
			0x712f2f5e, 0x97848413, 0xf55353a6, 0x68d1d1b9, 0x00000000,
			0x2cededc1, 0x60202040, 0x1ffcfce3, 0xc8b1b179, 0xed5b5bb6,
			0xbe6a6ad4, 0x46cbcb8d, 0xd9bebe67, 0x4b393972, 0xde4a4a94,
			0xd44c4c98, 0xe85858b0, 0x4acfcf85, 0x6bd0d0bb, 0x2aefefc5,
			0xe5aaaa4f, 0x16fbfbed, 0xc5434386, 0xd74d4d9a, 0x55333366,
			0x94858511, 0xcf45458a, 0x10f9f9e9, 0x06020204, 0x817f7ffe,
			0xf05050a0, 0x443c3c78, 0xba9f9f25, 0xe3a8a84b, 0xf35151a2,
			0xfea3a35d, 0xc0404080, 0x8a8f8f05, 0xad92923f, 0xbc9d9d21,
			0x48383870, 0x04f5f5f1, 0xdfbcbc63, 0xc1b6b677, 0x75dadaaf,
			0x63212142, 0x30101020, 0x1affffe5, 0x0ef3f3fd, 0x6dd2d2bf,
			0x4ccdcd81, 0x140c0c18, 0x35131326, 0x2fececc3, 0xe15f5fbe,
			0xa2979735, 0xcc444488, 0x3917172e, 0x57c4c493, 0xf2a7a755,
			0x827e7efc, 0x473d3d7a, 0xac6464c8, 0xe75d5dba, 0x2b191932,
			0x957373e6, 0xa06060c0, 0x98818119, 0xd14f4f9e, 0x7fdcdca3,
			0x66222244, 0x7e2a2a54, 0xab90903b, 0x8388880b, 0xca46468c,
			0x29eeeec7, 0xd3b8b86b, 0x3c141428, 0x79dedea7, 0xe25e5ebc,
			0x1d0b0b16, 0x76dbdbad, 0x3be0e0db, 0x56323264, 0x4e3a3a74,
			0x1e0a0a14, 0xdb494992, 0x0a06060c, 0x6c242448, 0xe45c5cb8,
			0x5dc2c29f, 0x6ed3d3bd, 0xefacac43, 0xa66262c4, 0xa8919139,
			0xa4959531, 0x37e4e4d3, 0x8b7979f2, 0x32e7e7d5, 0x43c8c88b,
			0x5937376e, 0xb76d6dda, 0x8c8d8d01, 0x64d5d5b1, 0xd24e4e9c,
			0xe0a9a949, 0xb46c6cd8, 0xfa5656ac, 0x07f4f4f3, 0x25eaeacf,
			0xaf6565ca, 0x8e7a7af4, 0xe9aeae47, 0x18080810, 0xd5baba6f,
			0x887878f0, 0x6f25254a, 0x722e2e5c, 0x241c1c38, 0xf1a6a657,
			0xc7b4b473, 0x51c6c697, 0x23e8e8cb, 0x7cdddda1, 0x9c7474e8,
			0x211f1f3e, 0xdd4b4b96, 0xdcbdbd61, 0x868b8b0d, 0x858a8a0f,
			0x907070e0, 0x423e3e7c, 0xc4b5b571, 0xaa6666cc, 0xd8484890,
			0x05030306, 0x01f6f6f7, 0x120e0e1c, 0xa36161c2, 0x5f35356a,
			0xf95757ae, 0xd0b9b969, 0x91868617, 0x58c1c199, 0x271d1d3a,
			0xb99e9e27, 0x38e1e1d9, 0x13f8f8eb, 0xb398982b, 0x33111122,
			0xbb6969d2, 0x70d9d9a9, 0x898e8e07, 0xa7949433, 0xb69b9b2d,
			0x221e1e3c, 0x92878715, 0x20e9e9c9, 0x49cece87, 0xff5555aa,
			0x78282850, 0x7adfdfa5, 0x8f8c8c03, 0xf8a1a159, 0x80898909,
			0x170d0d1a, 0xdabfbf65, 0x31e6e6d7, 0xc6424284, 0xb86868d0,
			0xc3414182, 0xb0999929, 0x772d2d5a, 0x110f0f1e, 0xcbb0b07b,
			0xfc5454a8, 0xd6bbbb6d, 0x3a16162c };

	private static final int[] T1 = { 0x6363c6a5, 0x7c7cf884, 0x7777ee99,
			0x7b7bf68d, 0xf2f2ff0d, 0x6b6bd6bd, 0x6f6fdeb1, 0xc5c59154,
			0x30306050, 0x01010203, 0x6767cea9, 0x2b2b567d, 0xfefee719,
			0xd7d7b562, 0xabab4de6, 0x7676ec9a, 0xcaca8f45, 0x82821f9d,
			0xc9c98940, 0x7d7dfa87, 0xfafaef15, 0x5959b2eb, 0x47478ec9,
			0xf0f0fb0b, 0xadad41ec, 0xd4d4b367, 0xa2a25ffd, 0xafaf45ea,
			0x9c9c23bf, 0xa4a453f7, 0x7272e496, 0xc0c09b5b, 0xb7b775c2,
			0xfdfde11c, 0x93933dae, 0x26264c6a, 0x36366c5a, 0x3f3f7e41,
			0xf7f7f502, 0xcccc834f, 0x3434685c, 0xa5a551f4, 0xe5e5d134,
			0xf1f1f908, 0x7171e293, 0xd8d8ab73, 0x31316253, 0x15152a3f,
			0x0404080c, 0xc7c79552, 0x23234665, 0xc3c39d5e, 0x18183028,
			0x969637a1, 0x05050a0f, 0x9a9a2fb5, 0x07070e09, 0x12122436,
			0x80801b9b, 0xe2e2df3d, 0xebebcd26, 0x27274e69, 0xb2b27fcd,
			0x7575ea9f, 0x0909121b, 0x83831d9e, 0x2c2c5874, 0x1a1a342e,
			0x1b1b362d, 0x6e6edcb2, 0x5a5ab4ee, 0xa0a05bfb, 0x5252a4f6,
			0x3b3b764d, 0xd6d6b761, 0xb3b37dce, 0x2929527b, 0xe3e3dd3e,
			0x2f2f5e71, 0x84841397, 0x5353a6f5, 0xd1d1b968, 0x00000000,
			0xededc12c, 0x20204060, 0xfcfce31f, 0xb1b179c8, 0x5b5bb6ed,
			0x6a6ad4be, 0xcbcb8d46, 0xbebe67d9, 0x3939724b, 0x4a4a94de,
			0x4c4c98d4, 0x5858b0e8, 0xcfcf854a, 0xd0d0bb6b, 0xefefc52a,
			0xaaaa4fe5, 0xfbfbed16, 0x434386c5, 0x4d4d9ad7, 0x33336655,
			0x85851194, 0x45458acf, 0xf9f9e910, 0x02020406, 0x7f7ffe81,
			0x5050a0f0, 0x3c3c7844, 0x9f9f25ba, 0xa8a84be3, 0x5151a2f3,
			0xa3a35dfe, 0x404080c0, 0x8f8f058a, 0x92923fad, 0x9d9d21bc,
			0x38387048, 0xf5f5f104, 0xbcbc63df, 0xb6b677c1, 0xdadaaf75,
			0x21214263, 0x10102030, 0xffffe51a, 0xf3f3fd0e, 0xd2d2bf6d,
			0xcdcd814c, 0x0c0c1814, 0x13132635, 0xececc32f, 0x5f5fbee1,
			0x979735a2, 0x444488cc, 0x17172e39, 0xc4c49357, 0xa7a755f2,
			0x7e7efc82, 0x3d3d7a47, 0x6464c8ac, 0x5d5dbae7, 0x1919322b,
			0x7373e695, 0x6060c0a0, 0x81811998, 0x4f4f9ed1, 0xdcdca37f,
			0x22224466, 0x2a2a547e, 0x90903bab, 0x88880b83, 0x46468cca,
			0xeeeec729, 0xb8b86bd3, 0x1414283c, 0xdedea779, 0x5e5ebce2,
			0x0b0b161d, 0xdbdbad76, 0xe0e0db3b, 0x32326456, 0x3a3a744e,
			0x0a0a141e, 0x494992db, 0x06060c0a, 0x2424486c, 0x5c5cb8e4,
			0xc2c29f5d, 0xd3d3bd6e, 0xacac43ef, 0x6262c4a6, 0x919139a8,
			0x959531a4, 0xe4e4d337, 0x7979f28b, 0xe7e7d532, 0xc8c88b43,
			0x37376e59, 0x6d6ddab7, 0x8d8d018c, 0xd5d5b164, 0x4e4e9cd2,
			0xa9a949e0, 0x6c6cd8b4, 0x5656acfa, 0xf4f4f307, 0xeaeacf25,
			0x6565caaf, 0x7a7af48e, 0xaeae47e9, 0x08081018, 0xbaba6fd5,
			0x7878f088, 0x25254a6f, 0x2e2e5c72, 0x1c1c3824, 0xa6a657f1,
			0xb4b473c7, 0xc6c69751, 0xe8e8cb23, 0xdddda17c, 0x7474e89c,
			0x1f1f3e21, 0x4b4b96dd, 0xbdbd61dc, 0x8b8b0d86, 0x8a8a0f85,
			0x7070e090, 0x3e3e7c42, 0xb5b571c4, 0x6666ccaa, 0x484890d8,
			0x03030605, 0xf6f6f701, 0x0e0e1c12, 0x6161c2a3, 0x35356a5f,
			0x5757aef9, 0xb9b969d0, 0x86861791, 0xc1c19958, 0x1d1d3a27,
			0x9e9e27b9, 0xe1e1d938, 0xf8f8eb13, 0x98982bb3, 0x11112233,
			0x6969d2bb, 0xd9d9a970, 0x8e8e0789, 0x949433a7, 0x9b9b2db6,
			0x1e1e3c22, 0x87871592, 0xe9e9c920, 0xcece8749, 0x5555aaff,
			0x28285078, 0xdfdfa57a, 0x8c8c038f, 0xa1a159f8, 0x89890980,
			0x0d0d1a17, 0xbfbf65da, 0xe6e6d731, 0x424284c6, 0x6868d0b8,
			0x414182c3, 0x999929b0, 0x2d2d5a77, 0x0f0f1e11, 0xb0b07bcb,
			0x5454a8fc, 0xbbbb6dd6, 0x16162c3a };

	private static final int[] T2 = { 0x63c6a563, 0x7cf8847c, 0x77ee9977,
			0x7bf68d7b, 0xf2ff0df2, 0x6bd6bd6b, 0x6fdeb16f, 0xc59154c5,
			0x30605030, 0x01020301, 0x67cea967, 0x2b567d2b, 0xfee719fe,
			0xd7b562d7, 0xab4de6ab, 0x76ec9a76, 0xca8f45ca, 0x821f9d82,
			0xc98940c9, 0x7dfa877d, 0xfaef15fa, 0x59b2eb59, 0x478ec947,
			0xf0fb0bf0, 0xad41ecad, 0xd4b367d4, 0xa25ffda2, 0xaf45eaaf,
			0x9c23bf9c, 0xa453f7a4, 0x72e49672, 0xc09b5bc0, 0xb775c2b7,
			0xfde11cfd, 0x933dae93, 0x264c6a26, 0x366c5a36, 0x3f7e413f,
			0xf7f502f7, 0xcc834fcc, 0x34685c34, 0xa551f4a5, 0xe5d134e5,
			0xf1f908f1, 0x71e29371, 0xd8ab73d8, 0x31625331, 0x152a3f15,
			0x04080c04, 0xc79552c7, 0x23466523, 0xc39d5ec3, 0x18302818,
			0x9637a196, 0x050a0f05, 0x9a2fb59a, 0x070e0907, 0x12243612,
			0x801b9b80, 0xe2df3de2, 0xebcd26eb, 0x274e6927, 0xb27fcdb2,
			0x75ea9f75, 0x09121b09, 0x831d9e83, 0x2c58742c, 0x1a342e1a,
			0x1b362d1b, 0x6edcb26e, 0x5ab4ee5a, 0xa05bfba0, 0x52a4f652,
			0x3b764d3b, 0xd6b761d6, 0xb37dceb3, 0x29527b29, 0xe3dd3ee3,
			0x2f5e712f, 0x84139784, 0x53a6f553, 0xd1b968d1, 0x00000000,
			0xedc12ced, 0x20406020, 0xfce31ffc, 0xb179c8b1, 0x5bb6ed5b,
			0x6ad4be6a, 0xcb8d46cb, 0xbe67d9be, 0x39724b39, 0x4a94de4a,
			0x4c98d44c, 0x58b0e858, 0xcf854acf, 0xd0bb6bd0, 0xefc52aef,
			0xaa4fe5aa, 0xfbed16fb, 0x4386c543, 0x4d9ad74d, 0x33665533,
			0x85119485, 0x458acf45, 0xf9e910f9, 0x02040602, 0x7ffe817f,
			0x50a0f050, 0x3c78443c, 0x9f25ba9f, 0xa84be3a8, 0x51a2f351,
			0xa35dfea3, 0x4080c040, 0x8f058a8f, 0x923fad92, 0x9d21bc9d,
			0x38704838, 0xf5f104f5, 0xbc63dfbc, 0xb677c1b6, 0xdaaf75da,
			0x21426321, 0x10203010, 0xffe51aff, 0xf3fd0ef3, 0xd2bf6dd2,
			0xcd814ccd, 0x0c18140c, 0x13263513, 0xecc32fec, 0x5fbee15f,
			0x9735a297, 0x4488cc44, 0x172e3917, 0xc49357c4, 0xa755f2a7,
			0x7efc827e, 0x3d7a473d, 0x64c8ac64, 0x5dbae75d, 0x19322b19,
			0x73e69573, 0x60c0a060, 0x81199881, 0x4f9ed14f, 0xdca37fdc,
			0x22446622, 0x2a547e2a, 0x903bab90, 0x880b8388, 0x468cca46,
			0xeec729ee, 0xb86bd3b8, 0x14283c14, 0xdea779de, 0x5ebce25e,
			0x0b161d0b, 0xdbad76db, 0xe0db3be0, 0x32645632, 0x3a744e3a,
			0x0a141e0a, 0x4992db49, 0x060c0a06, 0x24486c24, 0x5cb8e45c,
			0xc29f5dc2, 0xd3bd6ed3, 0xac43efac, 0x62c4a662, 0x9139a891,
			0x9531a495, 0xe4d337e4, 0x79f28b79, 0xe7d532e7, 0xc88b43c8,
			0x376e5937, 0x6ddab76d, 0x8d018c8d, 0xd5b164d5, 0x4e9cd24e,
			0xa949e0a9, 0x6cd8b46c, 0x56acfa56, 0xf4f307f4, 0xeacf25ea,
			0x65caaf65, 0x7af48e7a, 0xae47e9ae, 0x08101808, 0xba6fd5ba,
			0x78f08878, 0x254a6f25, 0x2e5c722e, 0x1c38241c, 0xa657f1a6,
			0xb473c7b4, 0xc69751c6, 0xe8cb23e8, 0xdda17cdd, 0x74e89c74,
			0x1f3e211f, 0x4b96dd4b, 0xbd61dcbd, 0x8b0d868b, 0x8a0f858a,
			0x70e09070, 0x3e7c423e, 0xb571c4b5, 0x66ccaa66, 0x4890d848,
			0x03060503, 0xf6f701f6, 0x0e1c120e, 0x61c2a361, 0x356a5f35,
			0x57aef957, 0xb969d0b9, 0x86179186, 0xc19958c1, 0x1d3a271d,
			0x9e27b99e, 0xe1d938e1, 0xf8eb13f8, 0x982bb398, 0x11223311,
			0x69d2bb69, 0xd9a970d9, 0x8e07898e, 0x9433a794, 0x9b2db69b,
			0x1e3c221e, 0x87159287, 0xe9c920e9, 0xce8749ce, 0x55aaff55,
			0x28507828, 0xdfa57adf, 0x8c038f8c, 0xa159f8a1, 0x89098089,
			0x0d1a170d, 0xbf65dabf, 0xe6d731e6, 0x4284c642, 0x68d0b868,
			0x4182c341, 0x9929b099, 0x2d5a772d, 0x0f1e110f, 0xb07bcbb0,
			0x54a8fc54, 0xbb6dd6bb, 0x162c3a16 };

	private static final int[] T3 = { 0xc6a56363, 0xf8847c7c, 0xee997777,
			0xf68d7b7b, 0xff0df2f2, 0xd6bd6b6b, 0xdeb16f6f, 0x9154c5c5,
			0x60503030, 0x02030101, 0xcea96767, 0x567d2b2b, 0xe719fefe,
			0xb562d7d7, 0x4de6abab, 0xec9a7676, 0x8f45caca, 0x1f9d8282,
			0x8940c9c9, 0xfa877d7d, 0xef15fafa, 0xb2eb5959, 0x8ec94747,
			0xfb0bf0f0, 0x41ecadad, 0xb367d4d4, 0x5ffda2a2, 0x45eaafaf,
			0x23bf9c9c, 0x53f7a4a4, 0xe4967272, 0x9b5bc0c0, 0x75c2b7b7,
			0xe11cfdfd, 0x3dae9393, 0x4c6a2626, 0x6c5a3636, 0x7e413f3f,
			0xf502f7f7, 0x834fcccc, 0x685c3434, 0x51f4a5a5, 0xd134e5e5,
			0xf908f1f1, 0xe2937171, 0xab73d8d8, 0x62533131, 0x2a3f1515,
			0x080c0404, 0x9552c7c7, 0x46652323, 0x9d5ec3c3, 0x30281818,
			0x37a19696, 0x0a0f0505, 0x2fb59a9a, 0x0e090707, 0x24361212,
			0x1b9b8080, 0xdf3de2e2, 0xcd26ebeb, 0x4e692727, 0x7fcdb2b2,
			0xea9f7575, 0x121b0909, 0x1d9e8383, 0x58742c2c, 0x342e1a1a,
			0x362d1b1b, 0xdcb26e6e, 0xb4ee5a5a, 0x5bfba0a0, 0xa4f65252,
			0x764d3b3b, 0xb761d6d6, 0x7dceb3b3, 0x527b2929, 0xdd3ee3e3,
			0x5e712f2f, 0x13978484, 0xa6f55353, 0xb968d1d1, 0x00000000,
			0xc12ceded, 0x40602020, 0xe31ffcfc, 0x79c8b1b1, 0xb6ed5b5b,
			0xd4be6a6a, 0x8d46cbcb, 0x67d9bebe, 0x724b3939, 0x94de4a4a,
			0x98d44c4c, 0xb0e85858, 0x854acfcf, 0xbb6bd0d0, 0xc52aefef,
			0x4fe5aaaa, 0xed16fbfb, 0x86c54343, 0x9ad74d4d, 0x66553333,
			0x11948585, 0x8acf4545, 0xe910f9f9, 0x04060202, 0xfe817f7f,
			0xa0f05050, 0x78443c3c, 0x25ba9f9f, 0x4be3a8a8, 0xa2f35151,
			0x5dfea3a3, 0x80c04040, 0x058a8f8f, 0x3fad9292, 0x21bc9d9d,
			0x70483838, 0xf104f5f5, 0x63dfbcbc, 0x77c1b6b6, 0xaf75dada,
			0x42632121, 0x20301010, 0xe51affff, 0xfd0ef3f3, 0xbf6dd2d2,
			0x814ccdcd, 0x18140c0c, 0x26351313, 0xc32fecec, 0xbee15f5f,
			0x35a29797, 0x88cc4444, 0x2e391717, 0x9357c4c4, 0x55f2a7a7,
			0xfc827e7e, 0x7a473d3d, 0xc8ac6464, 0xbae75d5d, 0x322b1919,
			0xe6957373, 0xc0a06060, 0x19988181, 0x9ed14f4f, 0xa37fdcdc,
			0x44662222, 0x547e2a2a, 0x3bab9090, 0x0b838888, 0x8cca4646,
			0xc729eeee, 0x6bd3b8b8, 0x283c1414, 0xa779dede, 0xbce25e5e,
			0x161d0b0b, 0xad76dbdb, 0xdb3be0e0, 0x64563232, 0x744e3a3a,
			0x141e0a0a, 0x92db4949, 0x0c0a0606, 0x486c2424, 0xb8e45c5c,
			0x9f5dc2c2, 0xbd6ed3d3, 0x43efacac, 0xc4a66262, 0x39a89191,
			0x31a49595, 0xd337e4e4, 0xf28b7979, 0xd532e7e7, 0x8b43c8c8,
			0x6e593737, 0xdab76d6d, 0x018c8d8d, 0xb164d5d5, 0x9cd24e4e,
			0x49e0a9a9, 0xd8b46c6c, 0xacfa5656, 0xf307f4f4, 0xcf25eaea,
			0xcaaf6565, 0xf48e7a7a, 0x47e9aeae, 0x10180808, 0x6fd5baba,
			0xf0887878, 0x4a6f2525, 0x5c722e2e, 0x38241c1c, 0x57f1a6a6,
			0x73c7b4b4, 0x9751c6c6, 0xcb23e8e8, 0xa17cdddd, 0xe89c7474,
			0x3e211f1f, 0x96dd4b4b, 0x61dcbdbd, 0x0d868b8b, 0x0f858a8a,
			0xe0907070, 0x7c423e3e, 0x71c4b5b5, 0xccaa6666, 0x90d84848,
			0x06050303, 0xf701f6f6, 0x1c120e0e, 0xc2a36161, 0x6a5f3535,
			0xaef95757, 0x69d0b9b9, 0x17918686, 0x9958c1c1, 0x3a271d1d,
			0x27b99e9e, 0xd938e1e1, 0xeb13f8f8, 0x2bb39898, 0x22331111,
			0xd2bb6969, 0xa970d9d9, 0x07898e8e, 0x33a79494, 0x2db69b9b,
			0x3c221e1e, 0x15928787, 0xc920e9e9, 0x8749cece, 0xaaff5555,
			0x50782828, 0xa57adfdf, 0x038f8c8c, 0x59f8a1a1, 0x09808989,
			0x1a170d0d, 0x65dabfbf, 0xd731e6e6, 0x84c64242, 0xd0b86868,
			0x82c34141, 0x29b09999, 0x5a772d2d, 0x1e110f0f, 0x7bcbb0b0,
			0xa8fc5454, 0x6dd6bbbb, 0x2c3a1616 };

	private static final int[] Tinv0 = { 0x50a7f451, 0x5365417e, 0xc3a4171a,
			0x965e273a, 0xcb6bab3b, 0xf1459d1f, 0xab58faac, 0x9303e34b,
			0x55fa3020, 0xf66d76ad, 0x9176cc88, 0x254c02f5, 0xfcd7e54f,
			0xd7cb2ac5, 0x80443526, 0x8fa362b5, 0x495ab1de, 0x671bba25,
			0x980eea45, 0xe1c0fe5d, 0x02752fc3, 0x12f04c81, 0xa397468d,
			0xc6f9d36b, 0xe75f8f03, 0x959c9215, 0xeb7a6dbf, 0xda595295,
			0x2d83bed4, 0xd3217458, 0x2969e049, 0x44c8c98e, 0x6a89c275,
			0x78798ef4, 0x6b3e5899, 0xdd71b927, 0xb64fe1be, 0x17ad88f0,
			0x66ac20c9, 0xb43ace7d, 0x184adf63, 0x82311ae5, 0x60335197,
			0x457f5362, 0xe07764b1, 0x84ae6bbb, 0x1ca081fe, 0x942b08f9,
			0x58684870, 0x19fd458f, 0x876cde94, 0xb7f87b52, 0x23d373ab,
			0xe2024b72, 0x578f1fe3, 0x2aab5566, 0x0728ebb2, 0x03c2b52f,
			0x9a7bc586, 0xa50837d3, 0xf2872830, 0xb2a5bf23, 0xba6a0302,
			0x5c8216ed, 0x2b1ccf8a, 0x92b479a7, 0xf0f207f3, 0xa1e2694e,
			0xcdf4da65, 0xd5be0506, 0x1f6234d1, 0x8afea6c4, 0x9d532e34,
			0xa055f3a2, 0x32e18a05, 0x75ebf6a4, 0x39ec830b, 0xaaef6040,
			0x069f715e, 0x51106ebd, 0xf98a213e, 0x3d06dd96, 0xae053edd,
			0x46bde64d, 0xb58d5491, 0x055dc471, 0x6fd40604, 0xff155060,
			0x24fb9819, 0x97e9bdd6, 0xcc434089, 0x779ed967, 0xbd42e8b0,
			0x888b8907, 0x385b19e7, 0xdbeec879, 0x470a7ca1, 0xe90f427c,
			0xc91e84f8, 0x00000000, 0x83868009, 0x48ed2b32, 0xac70111e,
			0x4e725a6c, 0xfbff0efd, 0x5638850f, 0x1ed5ae3d, 0x27392d36,
			0x64d90f0a, 0x21a65c68, 0xd1545b9b, 0x3a2e3624, 0xb1670a0c,
			0x0fe75793, 0xd296eeb4, 0x9e919b1b, 0x4fc5c080, 0xa220dc61,
			0x694b775a, 0x161a121c, 0x0aba93e2, 0xe52aa0c0, 0x43e0223c,
			0x1d171b12, 0x0b0d090e, 0xadc78bf2, 0xb9a8b62d, 0xc8a91e14,
			0x8519f157, 0x4c0775af, 0xbbdd99ee, 0xfd607fa3, 0x9f2601f7,
			0xbcf5725c, 0xc53b6644, 0x347efb5b, 0x7629438b, 0xdcc623cb,
			0x68fcedb6, 0x63f1e4b8, 0xcadc31d7, 0x10856342, 0x40229713,
			0x2011c684, 0x7d244a85, 0xf83dbbd2, 0x1132f9ae, 0x6da129c7,
			0x4b2f9e1d, 0xf330b2dc, 0xec52860d, 0xd0e3c177, 0x6c16b32b,
			0x99b970a9, 0xfa489411, 0x2264e947, 0xc48cfca8, 0x1a3ff0a0,
			0xd82c7d56, 0xef903322, 0xc74e4987, 0xc1d138d9, 0xfea2ca8c,
			0x360bd498, 0xcf81f5a6, 0x28de7aa5, 0x268eb7da, 0xa4bfad3f,
			0xe49d3a2c, 0x0d927850, 0x9bcc5f6a, 0x62467e54, 0xc2138df6,
			0xe8b8d890, 0x5ef7392e, 0xf5afc382, 0xbe805d9f, 0x7c93d069,
			0xa92dd56f, 0xb31225cf, 0x3b99acc8, 0xa77d1810, 0x6e639ce8,
			0x7bbb3bdb, 0x097826cd, 0xf418596e, 0x01b79aec, 0xa89a4f83,
			0x656e95e6, 0x7ee6ffaa, 0x08cfbc21, 0xe6e815ef, 0xd99be7ba,
			0xce366f4a, 0xd4099fea, 0xd67cb029, 0xafb2a431, 0x31233f2a,
			0x3094a5c6, 0xc066a235, 0x37bc4e74, 0xa6ca82fc, 0xb0d090e0,
			0x15d8a733, 0x4a9804f1, 0xf7daec41, 0x0e50cd7f, 0x2ff69117,
			0x8dd64d76, 0x4db0ef43, 0x544daacc, 0xdf0496e4, 0xe3b5d19e,
			0x1b886a4c, 0xb81f2cc1, 0x7f516546, 0x04ea5e9d, 0x5d358c01,
			0x737487fa, 0x2e410bfb, 0x5a1d67b3, 0x52d2db92, 0x335610e9,
			0x1347d66d, 0x8c61d79a, 0x7a0ca137, 0x8e14f859, 0x893c13eb,
			0xee27a9ce, 0x35c961b7, 0xede51ce1, 0x3cb1477a, 0x59dfd29c,
			0x3f73f255, 0x79ce1418, 0xbf37c773, 0xeacdf753, 0x5baafd5f,
			0x146f3ddf, 0x86db4478, 0x81f3afca, 0x3ec468b9, 0x2c342438,
			0x5f40a3c2, 0x72c31d16, 0x0c25e2bc, 0x8b493c28, 0x41950dff,
			0x7101a839, 0xdeb30c08, 0x9ce4b4d8, 0x90c15664, 0x6184cb7b,
			0x70b632d5, 0x745c6c48, 0x4257b8d0 };

	/* multiply four bytes in GF(2^8) by 'x' {02} in parallel */

	private static final int[] Tinv1 = { 0xa7f45150, 0x65417e53, 0xa4171ac3,
			0x5e273a96, 0x6bab3bcb, 0x459d1ff1, 0x58faacab, 0x03e34b93,
			0xfa302055, 0x6d76adf6, 0x76cc8891, 0x4c02f525, 0xd7e54ffc,
			0xcb2ac5d7, 0x44352680, 0xa362b58f, 0x5ab1de49, 0x1bba2567,
			0x0eea4598, 0xc0fe5de1, 0x752fc302, 0xf04c8112, 0x97468da3,
			0xf9d36bc6, 0x5f8f03e7, 0x9c921595, 0x7a6dbfeb, 0x595295da,
			0x83bed42d, 0x217458d3, 0x69e04929, 0xc8c98e44, 0x89c2756a,
			0x798ef478, 0x3e58996b, 0x71b927dd, 0x4fe1beb6, 0xad88f017,
			0xac20c966, 0x3ace7db4, 0x4adf6318, 0x311ae582, 0x33519760,
			0x7f536245, 0x7764b1e0, 0xae6bbb84, 0xa081fe1c, 0x2b08f994,
			0x68487058, 0xfd458f19, 0x6cde9487, 0xf87b52b7, 0xd373ab23,
			0x024b72e2, 0x8f1fe357, 0xab55662a, 0x28ebb207, 0xc2b52f03,
			0x7bc5869a, 0x0837d3a5, 0x872830f2, 0xa5bf23b2, 0x6a0302ba,
			0x8216ed5c, 0x1ccf8a2b, 0xb479a792, 0xf207f3f0, 0xe2694ea1,
			0xf4da65cd, 0xbe0506d5, 0x6234d11f, 0xfea6c48a, 0x532e349d,
			0x55f3a2a0, 0xe18a0532, 0xebf6a475, 0xec830b39, 0xef6040aa,
			0x9f715e06, 0x106ebd51, 0x8a213ef9, 0x06dd963d, 0x053eddae,
			0xbde64d46, 0x8d5491b5, 0x5dc47105, 0xd406046f, 0x155060ff,
			0xfb981924, 0xe9bdd697, 0x434089cc, 0x9ed96777, 0x42e8b0bd,
			0x8b890788, 0x5b19e738, 0xeec879db, 0x0a7ca147, 0x0f427ce9,
			0x1e84f8c9, 0x00000000, 0x86800983, 0xed2b3248, 0x70111eac,
			0x725a6c4e, 0xff0efdfb, 0x38850f56, 0xd5ae3d1e, 0x392d3627,
			0xd90f0a64, 0xa65c6821, 0x545b9bd1, 0x2e36243a, 0x670a0cb1,
			0xe757930f, 0x96eeb4d2, 0x919b1b9e, 0xc5c0804f, 0x20dc61a2,
			0x4b775a69, 0x1a121c16, 0xba93e20a, 0x2aa0c0e5, 0xe0223c43,
			0x171b121d, 0x0d090e0b, 0xc78bf2ad, 0xa8b62db9, 0xa91e14c8,
			0x19f15785, 0x0775af4c, 0xdd99eebb, 0x607fa3fd, 0x2601f79f,
			0xf5725cbc, 0x3b6644c5, 0x7efb5b34, 0x29438b76, 0xc623cbdc,
			0xfcedb668, 0xf1e4b863, 0xdc31d7ca, 0x85634210, 0x22971340,
			0x11c68420, 0x244a857d, 0x3dbbd2f8, 0x32f9ae11, 0xa129c76d,
			0x2f9e1d4b, 0x30b2dcf3, 0x52860dec, 0xe3c177d0, 0x16b32b6c,
			0xb970a999, 0x489411fa, 0x64e94722, 0x8cfca8c4, 0x3ff0a01a,
			0x2c7d56d8, 0x903322ef, 0x4e4987c7, 0xd138d9c1, 0xa2ca8cfe,
			0x0bd49836, 0x81f5a6cf, 0xde7aa528, 0x8eb7da26, 0xbfad3fa4,
			0x9d3a2ce4, 0x9278500d, 0xcc5f6a9b, 0x467e5462, 0x138df6c2,
			0xb8d890e8, 0xf7392e5e, 0xafc382f5, 0x805d9fbe, 0x93d0697c,
			0x2dd56fa9, 0x1225cfb3, 0x99acc83b, 0x7d1810a7, 0x639ce86e,
			0xbb3bdb7b, 0x7826cd09, 0x18596ef4, 0xb79aec01, 0x9a4f83a8,
			0x6e95e665, 0xe6ffaa7e, 0xcfbc2108, 0xe815efe6, 0x9be7bad9,
			0x366f4ace, 0x099fead4, 0x7cb029d6, 0xb2a431af, 0x233f2a31,
			0x94a5c630, 0x66a235c0, 0xbc4e7437, 0xca82fca6, 0xd090e0b0,
			0xd8a73315, 0x9804f14a, 0xdaec41f7, 0x50cd7f0e, 0xf691172f,
			0xd64d768d, 0xb0ef434d, 0x4daacc54, 0x0496e4df, 0xb5d19ee3,
			0x886a4c1b, 0x1f2cc1b8, 0x5165467f, 0xea5e9d04, 0x358c015d,
			0x7487fa73, 0x410bfb2e, 0x1d67b35a, 0xd2db9252, 0x5610e933,
			0x47d66d13, 0x61d79a8c, 0x0ca1377a, 0x14f8598e, 0x3c13eb89,
			0x27a9ceee, 0xc961b735, 0xe51ce1ed, 0xb1477a3c, 0xdfd29c59,
			0x73f2553f, 0xce141879, 0x37c773bf, 0xcdf753ea, 0xaafd5f5b,
			0x6f3ddf14, 0xdb447886, 0xf3afca81, 0xc468b93e, 0x3424382c,
			0x40a3c25f, 0xc31d1672, 0x25e2bc0c, 0x493c288b, 0x950dff41,
			0x01a83971, 0xb30c08de, 0xe4b4d89c, 0xc1566490, 0x84cb7b61,
			0xb632d570, 0x5c6c4874, 0x57b8d042 };
	private static final int[] Tinv2 = { 0xf45150a7, 0x417e5365, 0x171ac3a4,
			0x273a965e, 0xab3bcb6b, 0x9d1ff145, 0xfaacab58, 0xe34b9303,
			0x302055fa, 0x76adf66d, 0xcc889176, 0x02f5254c, 0xe54ffcd7,
			0x2ac5d7cb, 0x35268044, 0x62b58fa3, 0xb1de495a, 0xba25671b,
			0xea45980e, 0xfe5de1c0, 0x2fc30275, 0x4c8112f0, 0x468da397,
			0xd36bc6f9, 0x8f03e75f, 0x9215959c, 0x6dbfeb7a, 0x5295da59,
			0xbed42d83, 0x7458d321, 0xe0492969, 0xc98e44c8, 0xc2756a89,
			0x8ef47879, 0x58996b3e, 0xb927dd71, 0xe1beb64f, 0x88f017ad,
			0x20c966ac, 0xce7db43a, 0xdf63184a, 0x1ae58231, 0x51976033,
			0x5362457f, 0x64b1e077, 0x6bbb84ae, 0x81fe1ca0, 0x08f9942b,
			0x48705868, 0x458f19fd, 0xde94876c, 0x7b52b7f8, 0x73ab23d3,
			0x4b72e202, 0x1fe3578f, 0x55662aab, 0xebb20728, 0xb52f03c2,
			0xc5869a7b, 0x37d3a508, 0x2830f287, 0xbf23b2a5, 0x0302ba6a,
			0x16ed5c82, 0xcf8a2b1c, 0x79a792b4, 0x07f3f0f2, 0x694ea1e2,
			0xda65cdf4, 0x0506d5be, 0x34d11f62, 0xa6c48afe, 0x2e349d53,
			0xf3a2a055, 0x8a0532e1, 0xf6a475eb, 0x830b39ec, 0x6040aaef,
			0x715e069f, 0x6ebd5110, 0x213ef98a, 0xdd963d06, 0x3eddae05,
			0xe64d46bd, 0x5491b58d, 0xc471055d, 0x06046fd4, 0x5060ff15,
			0x981924fb, 0xbdd697e9, 0x4089cc43, 0xd967779e, 0xe8b0bd42,
			0x8907888b, 0x19e7385b, 0xc879dbee, 0x7ca1470a, 0x427ce90f,
			0x84f8c91e, 0x00000000, 0x80098386, 0x2b3248ed, 0x111eac70,
			0x5a6c4e72, 0x0efdfbff, 0x850f5638, 0xae3d1ed5, 0x2d362739,
			0x0f0a64d9, 0x5c6821a6, 0x5b9bd154, 0x36243a2e, 0x0a0cb167,
			0x57930fe7, 0xeeb4d296, 0x9b1b9e91, 0xc0804fc5, 0xdc61a220,
			0x775a694b, 0x121c161a, 0x93e20aba, 0xa0c0e52a, 0x223c43e0,
			0x1b121d17, 0x090e0b0d, 0x8bf2adc7, 0xb62db9a8, 0x1e14c8a9,
			0xf1578519, 0x75af4c07, 0x99eebbdd, 0x7fa3fd60, 0x01f79f26,
			0x725cbcf5, 0x6644c53b, 0xfb5b347e, 0x438b7629, 0x23cbdcc6,
			0xedb668fc, 0xe4b863f1, 0x31d7cadc, 0x63421085, 0x97134022,
			0xc6842011, 0x4a857d24, 0xbbd2f83d, 0xf9ae1132, 0x29c76da1,
			0x9e1d4b2f, 0xb2dcf330, 0x860dec52, 0xc177d0e3, 0xb32b6c16,
			0x70a999b9, 0x9411fa48, 0xe9472264, 0xfca8c48c, 0xf0a01a3f,
			0x7d56d82c, 0x3322ef90, 0x4987c74e, 0x38d9c1d1, 0xca8cfea2,
			0xd498360b, 0xf5a6cf81, 0x7aa528de, 0xb7da268e, 0xad3fa4bf,
			0x3a2ce49d, 0x78500d92, 0x5f6a9bcc, 0x7e546246, 0x8df6c213,
			0xd890e8b8, 0x392e5ef7, 0xc382f5af, 0x5d9fbe80, 0xd0697c93,
			0xd56fa92d, 0x25cfb312, 0xacc83b99, 0x1810a77d, 0x9ce86e63,
			0x3bdb7bbb, 0x26cd0978, 0x596ef418, 0x9aec01b7, 0x4f83a89a,
			0x95e6656e, 0xffaa7ee6, 0xbc2108cf, 0x15efe6e8, 0xe7bad99b,
			0x6f4ace36, 0x9fead409, 0xb029d67c, 0xa431afb2, 0x3f2a3123,
			0xa5c63094, 0xa235c066, 0x4e7437bc, 0x82fca6ca, 0x90e0b0d0,
			0xa73315d8, 0x04f14a98, 0xec41f7da, 0xcd7f0e50, 0x91172ff6,
			0x4d768dd6, 0xef434db0, 0xaacc544d, 0x96e4df04, 0xd19ee3b5,
			0x6a4c1b88, 0x2cc1b81f, 0x65467f51, 0x5e9d04ea, 0x8c015d35,
			0x87fa7374, 0x0bfb2e41, 0x67b35a1d, 0xdb9252d2, 0x10e93356,
			0xd66d1347, 0xd79a8c61, 0xa1377a0c, 0xf8598e14, 0x13eb893c,
			0xa9ceee27, 0x61b735c9, 0x1ce1ede5, 0x477a3cb1, 0xd29c59df,
			0xf2553f73, 0x141879ce, 0xc773bf37, 0xf753eacd, 0xfd5f5baa,
			0x3ddf146f, 0x447886db, 0xafca81f3, 0x68b93ec4, 0x24382c34,
			0xa3c25f40, 0x1d1672c3, 0xe2bc0c25, 0x3c288b49, 0x0dff4195,
			0xa8397101, 0x0c08deb3, 0xb4d89ce4, 0x566490c1, 0xcb7b6184,
			0x32d570b6, 0x6c48745c, 0xb8d04257 };
	private static final int[] Tinv3 = { 0x5150a7f4, 0x7e536541, 0x1ac3a417,
			0x3a965e27, 0x3bcb6bab, 0x1ff1459d, 0xacab58fa, 0x4b9303e3,
			0x2055fa30, 0xadf66d76, 0x889176cc, 0xf5254c02, 0x4ffcd7e5,
			0xc5d7cb2a, 0x26804435, 0xb58fa362, 0xde495ab1, 0x25671bba,
			0x45980eea, 0x5de1c0fe, 0xc302752f, 0x8112f04c, 0x8da39746,
			0x6bc6f9d3, 0x03e75f8f, 0x15959c92, 0xbfeb7a6d, 0x95da5952,
			0xd42d83be, 0x58d32174, 0x492969e0, 0x8e44c8c9, 0x756a89c2,
			0xf478798e, 0x996b3e58, 0x27dd71b9, 0xbeb64fe1, 0xf017ad88,
			0xc966ac20, 0x7db43ace, 0x63184adf, 0xe582311a, 0x97603351,
			0x62457f53, 0xb1e07764, 0xbb84ae6b, 0xfe1ca081, 0xf9942b08,
			0x70586848, 0x8f19fd45, 0x94876cde, 0x52b7f87b, 0xab23d373,
			0x72e2024b, 0xe3578f1f, 0x662aab55, 0xb20728eb, 0x2f03c2b5,
			0x869a7bc5, 0xd3a50837, 0x30f28728, 0x23b2a5bf, 0x02ba6a03,
			0xed5c8216, 0x8a2b1ccf, 0xa792b479, 0xf3f0f207, 0x4ea1e269,
			0x65cdf4da, 0x06d5be05, 0xd11f6234, 0xc48afea6, 0x349d532e,
			0xa2a055f3, 0x0532e18a, 0xa475ebf6, 0x0b39ec83, 0x40aaef60,
			0x5e069f71, 0xbd51106e, 0x3ef98a21, 0x963d06dd, 0xddae053e,
			0x4d46bde6, 0x91b58d54, 0x71055dc4, 0x046fd406, 0x60ff1550,
			0x1924fb98, 0xd697e9bd, 0x89cc4340, 0x67779ed9, 0xb0bd42e8,
			0x07888b89, 0xe7385b19, 0x79dbeec8, 0xa1470a7c, 0x7ce90f42,
			0xf8c91e84, 0x00000000, 0x09838680, 0x3248ed2b, 0x1eac7011,
			0x6c4e725a, 0xfdfbff0e, 0x0f563885, 0x3d1ed5ae, 0x3627392d,
			0x0a64d90f, 0x6821a65c, 0x9bd1545b, 0x243a2e36, 0x0cb1670a,
			0x930fe757, 0xb4d296ee, 0x1b9e919b, 0x804fc5c0, 0x61a220dc,
			0x5a694b77, 0x1c161a12, 0xe20aba93, 0xc0e52aa0, 0x3c43e022,
			0x121d171b, 0x0e0b0d09, 0xf2adc78b, 0x2db9a8b6, 0x14c8a91e,
			0x578519f1, 0xaf4c0775, 0xeebbdd99, 0xa3fd607f, 0xf79f2601,
			0x5cbcf572, 0x44c53b66, 0x5b347efb, 0x8b762943, 0xcbdcc623,
			0xb668fced, 0xb863f1e4, 0xd7cadc31, 0x42108563, 0x13402297,
			0x842011c6, 0x857d244a, 0xd2f83dbb, 0xae1132f9, 0xc76da129,
			0x1d4b2f9e, 0xdcf330b2, 0x0dec5286, 0x77d0e3c1, 0x2b6c16b3,
			0xa999b970, 0x11fa4894, 0x472264e9, 0xa8c48cfc, 0xa01a3ff0,
			0x56d82c7d, 0x22ef9033, 0x87c74e49, 0xd9c1d138, 0x8cfea2ca,
			0x98360bd4, 0xa6cf81f5, 0xa528de7a, 0xda268eb7, 0x3fa4bfad,
			0x2ce49d3a, 0x500d9278, 0x6a9bcc5f, 0x5462467e, 0xf6c2138d,
			0x90e8b8d8, 0x2e5ef739, 0x82f5afc3, 0x9fbe805d, 0x697c93d0,
			0x6fa92dd5, 0xcfb31225, 0xc83b99ac, 0x10a77d18, 0xe86e639c,
			0xdb7bbb3b, 0xcd097826, 0x6ef41859, 0xec01b79a, 0x83a89a4f,
			0xe6656e95, 0xaa7ee6ff, 0x2108cfbc, 0xefe6e815, 0xbad99be7,
			0x4ace366f, 0xead4099f, 0x29d67cb0, 0x31afb2a4, 0x2a31233f,
			0xc63094a5, 0x35c066a2, 0x7437bc4e, 0xfca6ca82, 0xe0b0d090,
			0x3315d8a7, 0xf14a9804, 0x41f7daec, 0x7f0e50cd, 0x172ff691,
			0x768dd64d, 0x434db0ef, 0xcc544daa, 0xe4df0496, 0x9ee3b5d1,
			0x4c1b886a, 0xc1b81f2c, 0x467f5165, 0x9d04ea5e, 0x015d358c,
			0xfa737487, 0xfb2e410b, 0xb35a1d67, 0x9252d2db, 0xe9335610,
			0x6d1347d6, 0x9a8c61d7, 0x377a0ca1, 0x598e14f8, 0xeb893c13,
			0xceee27a9, 0xb735c961, 0xe1ede51c, 0x7a3cb147, 0x9c59dfd2,
			0x553f73f2, 0x1879ce14, 0x73bf37c7, 0x53eacdf7, 0x5f5baafd,
			0xdf146f3d, 0x7886db44, 0xca81f3af, 0xb93ec468, 0x382c3424,
			0xc25f40a3, 0x1672c31d, 0xbc0c25e2, 0x288b493c, 0xff41950d,
			0x397101a8, 0x08deb30c, 0xd89ce4b4, 0x6490c156, 0x7b6184cb,
			0xd570b632, 0x48745c6c, 0xd04257b8 };

	private int C0, C1, C2, C3;

	/*
	 * The following defines provide alternative definitions of FFmulX that
	 * might give improved performance if a fast 32-bit multiply is not
	 * available.
	 * 
	 * private int FFmulX(int x) { int u = x & m1; u |= (u >> 1); return ((x &
	 * m2) << 1) ^ ((u >>> 3) | (u >>> 6)); } private static final int m4 =
	 * 0x1b1b1b1b; private int FFmulX(int x) { int u = x & m1; return ((x & m2)
	 * << 1) ^ ((u - (u >>> 7)) & m4); }
	 */

	private boolean forEncryption;

	private int ROUNDS;

	private int[][] WorkingKey = null;

	private void decryptBlock(int[][] KW) {

		int r0, r1, r2, r3;

		C0 ^= KW[ROUNDS][0];
		C1 ^= KW[ROUNDS][1];
		C2 ^= KW[ROUNDS][2];
		C3 ^= KW[ROUNDS][3];

		int r = ROUNDS - 1;

		while (r > 1) {
			r0 = Tinv0[C0 & 255] ^ Tinv1[(C3 >> 8) & 255]
					^ Tinv2[(C2 >> 16) & 255] ^ Tinv3[(C1 >> 24) & 255]
					^ KW[r][0];
			r1 = Tinv0[C1 & 255] ^ Tinv1[(C0 >> 8) & 255]
					^ Tinv2[(C3 >> 16) & 255] ^ Tinv3[(C2 >> 24) & 255]
					^ KW[r][1];
			r2 = Tinv0[C2 & 255] ^ Tinv1[(C1 >> 8) & 255]
					^ Tinv2[(C0 >> 16) & 255] ^ Tinv3[(C3 >> 24) & 255]
					^ KW[r][2];
			r3 = Tinv0[C3 & 255] ^ Tinv1[(C2 >> 8) & 255]
					^ Tinv2[(C1 >> 16) & 255] ^ Tinv3[(C0 >> 24) & 255]
					^ KW[r--][3];
			C0 = Tinv0[r0 & 255] ^ Tinv1[(r3 >> 8) & 255]
					^ Tinv2[(r2 >> 16) & 255] ^ Tinv3[(r1 >> 24) & 255]
					^ KW[r][0];
			C1 = Tinv0[r1 & 255] ^ Tinv1[(r0 >> 8) & 255]
					^ Tinv2[(r3 >> 16) & 255] ^ Tinv3[(r2 >> 24) & 255]
					^ KW[r][1];
			C2 = Tinv0[r2 & 255] ^ Tinv1[(r1 >> 8) & 255]
					^ Tinv2[(r0 >> 16) & 255] ^ Tinv3[(r3 >> 24) & 255]
					^ KW[r][2];
			C3 = Tinv0[r3 & 255] ^ Tinv1[(r2 >> 8) & 255]
					^ Tinv2[(r1 >> 16) & 255] ^ Tinv3[(r0 >> 24) & 255]
					^ KW[r--][3];
		}

		r0 = Tinv0[C0 & 255] ^ Tinv1[(C3 >> 8) & 255] ^ Tinv2[(C2 >> 16) & 255]
				^ Tinv3[(C1 >> 24) & 255] ^ KW[r][0];
		r1 = Tinv0[C1 & 255] ^ Tinv1[(C0 >> 8) & 255] ^ Tinv2[(C3 >> 16) & 255]
				^ Tinv3[(C2 >> 24) & 255] ^ KW[r][1];
		r2 = Tinv0[C2 & 255] ^ Tinv1[(C1 >> 8) & 255] ^ Tinv2[(C0 >> 16) & 255]
				^ Tinv3[(C3 >> 24) & 255] ^ KW[r][2];
		r3 = Tinv0[C3 & 255] ^ Tinv1[(C2 >> 8) & 255] ^ Tinv2[(C1 >> 16) & 255]
				^ Tinv3[(C0 >> 24) & 255] ^ KW[r][3];

		// the final round's table is a simple function of Si so we don't use a
		// whole other four tables for it

		C0 = (Si[r0 & 255] & 255) ^ ((Si[(r3 >> 8) & 255] & 255) << 8)
				^ ((Si[(r2 >> 16) & 255] & 255) << 16)
				^ (Si[(r1 >> 24) & 255] << 24) ^ KW[0][0];
		C1 = (Si[r1 & 255] & 255) ^ ((Si[(r0 >> 8) & 255] & 255) << 8)
				^ ((Si[(r3 >> 16) & 255] & 255) << 16)
				^ (Si[(r2 >> 24) & 255] << 24) ^ KW[0][1];
		C2 = (Si[r2 & 255] & 255) ^ ((Si[(r1 >> 8) & 255] & 255) << 8)
				^ ((Si[(r0 >> 16) & 255] & 255) << 16)
				^ (Si[(r3 >> 24) & 255] << 24) ^ KW[0][2];
		C3 = (Si[r3 & 255] & 255) ^ ((Si[(r2 >> 8) & 255] & 255) << 8)
				^ ((Si[(r1 >> 16) & 255] & 255) << 16)
				^ (Si[(r0 >> 24) & 255] << 24) ^ KW[0][3];
	}

	private void encryptBlock(int[][] KW) {

		// org.noe.framework.util.log.Log.debug("--KWKWKWKWKWKWKWKWKWKW-----------------");
		int r, r0, r1, r2, r3;

		C0 ^= KW[0][0];
		C1 ^= KW[0][1];
		C2 ^= KW[0][2];
		C3 ^= KW[0][3];

		r = 1;
		while (r < ROUNDS - 1) {
			r0 = T0[C0 & 255] ^ T1[(C1 >> 8) & 255] ^ T2[(C2 >> 16) & 255]
					^ T3[(C3 >> 24) & 255] ^ KW[r][0];
			r1 = T0[C1 & 255] ^ T1[(C2 >> 8) & 255] ^ T2[(C3 >> 16) & 255]
					^ T3[(C0 >> 24) & 255] ^ KW[r][1];
			r2 = T0[C2 & 255] ^ T1[(C3 >> 8) & 255] ^ T2[(C0 >> 16) & 255]
					^ T3[(C1 >> 24) & 255] ^ KW[r][2];
			r3 = T0[C3 & 255] ^ T1[(C0 >> 8) & 255] ^ T2[(C1 >> 16) & 255]
					^ T3[(C2 >> 24) & 255] ^ KW[r++][3];
			C0 = T0[r0 & 255] ^ T1[(r1 >> 8) & 255] ^ T2[(r2 >> 16) & 255]
					^ T3[(r3 >> 24) & 255] ^ KW[r][0];
			C1 = T0[r1 & 255] ^ T1[(r2 >> 8) & 255] ^ T2[(r3 >> 16) & 255]
					^ T3[(r0 >> 24) & 255] ^ KW[r][1];
			C2 = T0[r2 & 255] ^ T1[(r3 >> 8) & 255] ^ T2[(r0 >> 16) & 255]
					^ T3[(r1 >> 24) & 255] ^ KW[r][2];
			C3 = T0[r3 & 255] ^ T1[(r0 >> 8) & 255] ^ T2[(r1 >> 16) & 255]
					^ T3[(r2 >> 24) & 255] ^ KW[r++][3];
		}

		r0 = T0[C0 & 255] ^ T1[(C1 >> 8) & 255] ^ T2[(C2 >> 16) & 255]
				^ T3[(C3 >> 24) & 255] ^ KW[r][0];
		r1 = T0[C1 & 255] ^ T1[(C2 >> 8) & 255] ^ T2[(C3 >> 16) & 255]
				^ T3[(C0 >> 24) & 255] ^ KW[r][1];
		r2 = T0[C2 & 255] ^ T1[(C3 >> 8) & 255] ^ T2[(C0 >> 16) & 255]
				^ T3[(C1 >> 24) & 255] ^ KW[r][2];
		r3 = T0[C3 & 255] ^ T1[(C0 >> 8) & 255] ^ T2[(C1 >> 16) & 255]
				^ T3[(C2 >> 24) & 255] ^ KW[r++][3];

		// the final round's table is a simple function of S so we don't use a
		// whole other four tables for it

		C0 = (S[r0 & 255] & 255) ^ ((S[(r1 >> 8) & 255] & 255) << 8)
				^ ((S[(r2 >> 16) & 255] & 255) << 16)
				^ (S[(r3 >> 24) & 255] << 24) ^ KW[r][0];
		C1 = (S[r1 & 255] & 255) ^ ((S[(r2 >> 8) & 255] & 255) << 8)
				^ ((S[(r3 >> 16) & 255] & 255) << 16)
				^ (S[(r0 >> 24) & 255] << 24) ^ KW[r][1];
		C2 = (S[r2 & 255] & 255) ^ ((S[(r3 >> 8) & 255] & 255) << 8)
				^ ((S[(r0 >> 16) & 255] & 255) << 16)
				^ (S[(r1 >> 24) & 255] << 24) ^ KW[r][2];
		C3 = (S[r3 & 255] & 255) ^ ((S[(r0 >> 8) & 255] & 255) << 8)
				^ ((S[(r1 >> 16) & 255] & 255) << 16)
				^ (S[(r2 >> 24) & 255] << 24) ^ KW[r][3];

	}

	private int FFmulX(int x) {
		return (((x & m2) << 1) ^ (((x & m1) >>> 7) * m3));
	}

	/**
	 * Calculate the necessary round keys The number of calculations depends on
	 * key size and block size AES specified a fixed block size of 128 bits and
	 * key sizes 128/192/256 bits This code is written assuming those are the
	 * only possible values
	 */
	private int[][] generateWorkingKey(byte[] key, boolean forEncryption) {

		// org.noe.framework.util.log.Log.debug("key==="+new
		// String(key)+"@@@"+forEncryption);

		int KC = key.length / 4; // key length in words
		int t;

		if (((KC != 4) && (KC != 6) && (KC != 8)) || ((KC * 4) != key.length)) {
			throw new IllegalArgumentException(
					"Key length not 128/192/256 bits.");
		}

		ROUNDS = KC + 6; // This is not always true for the generalized
							// Rijndael
		// that allows larger block sizes
		int[][] W = new int[ROUNDS + 1][4]; // 4 words in a block

		//
		// copy the key into the round key array
		//

		t = 0;
		int i = 0;
		while (i < key.length) {
			W[t >> 2][t & 3] = (key[i] & 0xff) | ((key[i + 1] & 0xff) << 8)
					| ((key[i + 2] & 0xff) << 16) | (key[i + 3] << 24);
			i += 4;
			t++;
		}

		//
		// while not enough round key material calculated
		// calculate new values
		//
		int k = (ROUNDS + 1) << 2;
		for (i = KC; (i < k); i++) {
			int temp = W[(i - 1) >> 2][(i - 1) & 3];
			if ((i % KC) == 0) {
				temp = subWord(shift(temp, 8)) ^ rcon[(i / KC) - 1];
			} else if ((KC > 6) && ((i % KC) == 4)) {
				temp = subWord(temp);
			}

			W[i >> 2][i & 3] = W[(i - KC) >> 2][(i - KC) & 3] ^ temp;
		}

		if (!forEncryption) {
			for (int j = 1; j < ROUNDS; j++) {
				for (i = 0; i < 4; i++) {
					W[j][i] = inv_mcol(W[j][i]);
				}
			}
		}

		return W;
	}

	private int getBlockSize() {
		// //org.noe.framework.util.log.Log.debug("public int getBlockSize()");
		return BLOCK_SIZE;
	}

	/**
	 * initialise an AES cipher.
	 * 
	 * @param forEncryption
	 *            whether or not we are for encryption.
	 * @param params
	 *            the parameters required to set up the cipher.
	 * @exception IllegalArgumentException
	 *                if the params argument is inappropriate.
	 */
	// public void init(boolean forEncryption, CipherParameters params) {
	// //org.noe.framework.util.log.Log.debug("public void init()");
	// //org.noe.framework.util.log.Log.debug();
	// if (params instanceof KeyParameter) {
	// WorkingKey = generateWorkingKey(((KeyParameter) params).getKey(),
	// forEncryption);
	//
	// this.forEncryption = forEncryption;
	// return;
	// }
	//
	// throw new IllegalArgumentException(
	// "invalid parameter passed to AES init - "
	// + params.getClass().getName());
	// }
	private void init(boolean forEncryption, byte[] key) {
		// org.noe.framework.util.log.Log.debug("public void init()");
		// org.noe.framework.util.log.Log.debug();

		WorkingKey = generateWorkingKey(key, forEncryption);

		this.forEncryption = forEncryption;
		return;

	}

	private int inv_mcol(int x) {
		int f2 = FFmulX(x);
		int f4 = FFmulX(f2);
		int f8 = FFmulX(f4);
		int f9 = x ^ f8;

		return f2 ^ f4 ^ f8 ^ shift(f2 ^ f9, 8) ^ shift(f4 ^ f9, 16)
				^ shift(f9, 24);
	}

	private void packBlock(byte[] bytes, int off) {
		int index = off;
		// System.out.printf("@@@@c0=%d c1=%d c4=%d c3=%d\n",C0,C1,C2,C3);
		// org.noe.framework.util.log.Log.debug("bytes:"+ new
		// String(Hex.encode(bytes))+"===");
		bytes[index++] = (byte) C0;
		bytes[index++] = (byte) (C0 >> 8);
		bytes[index++] = (byte) (C0 >> 16);
		bytes[index++] = (byte) (C0 >> 24);

		bytes[index++] = (byte) C1;
		bytes[index++] = (byte) (C1 >> 8);
		bytes[index++] = (byte) (C1 >> 16);
		bytes[index++] = (byte) (C1 >> 24);

		bytes[index++] = (byte) C2;
		bytes[index++] = (byte) (C2 >> 8);
		bytes[index++] = (byte) (C2 >> 16);
		bytes[index++] = (byte) (C2 >> 24);

		bytes[index++] = (byte) C3;
		bytes[index++] = (byte) (C3 >> 8);
		bytes[index++] = (byte) (C3 >> 16);
		bytes[index++] = (byte) (C3 >> 24);

		// org.noe.framework.util.log.Log.debug("bytes:"+ new
		// String(Hex.encode(bytes))+"===");
	}

	private int processBlock(byte[] in, int inOff, byte[] out, int outOff)
			throws Exception {
		// org.noe.framework.util.log.Log.debug(inOff);
		// org.noe.framework.util.log.Log.debug(outOff);
		// org.noe.framework.util.log.Log.debug("in="+in.length);
		// org.noe.framework.util.log.Log.debug("out="+out.length);
		// org.noe.framework.util.log.Log.debug("in="+new String(Hex.encode(in
		// )));
		// org.noe.framework.util.log.Log.debug("out="+new String(Hex.encode(out
		// )));
		if (WorkingKey == null) {
			throw new IllegalStateException("AES engine not initialised");
		}

		if ((inOff + (32 / 2)) > in.length) {
			throw new Exception("input buffer too short");
		}

		if ((outOff + (32 / 2)) > out.length) {
			throw new Exception("output buffer too short");
		}

		if (forEncryption) {

			unpackBlock(in, inOff);

			encryptBlock(WorkingKey);
			packBlock(out, outOff);
		} else {

			unpackBlock(in, inOff);
			decryptBlock(WorkingKey);
			packBlock(out, outOff);
		}

		return BLOCK_SIZE;
	}

	private int shift(int r, int shift) {
		return (r >>> shift) | (r << -shift);
	}

	private int subWord(int x) {
		return (S[x & 255] & 255 | ((S[(x >> 8) & 255] & 255) << 8)
				| ((S[(x >> 16) & 255] & 255) << 16) | S[(x >> 24) & 255] << 24);
	}

	private void unpackBlock(byte[] bytes, int off) {
		int index = off;

		C0 = (bytes[index++] & 0xff);
		C0 |= (bytes[index++] & 0xff) << 8;
		C0 |= (bytes[index++] & 0xff) << 16;
		C0 |= bytes[index++] << 24;

		C1 = (bytes[index++] & 0xff);
		C1 |= (bytes[index++] & 0xff) << 8;
		C1 |= (bytes[index++] & 0xff) << 16;
		C1 |= bytes[index++] << 24;

		C2 = (bytes[index++] & 0xff);
		C2 |= (bytes[index++] & 0xff) << 8;
		C2 |= (bytes[index++] & 0xff) << 16;
		C2 |= bytes[index++] << 24;

		C3 = (bytes[index++] & 0xff);
		C3 |= (bytes[index++] & 0xff) << 8;
		C3 |= (bytes[index++] & 0xff) << 16;
		C3 |= bytes[index++] << 24;
	}

	private byte[] aesEncode(byte[] in, byte[] key) throws Exception {
		init(true, key);
		int blockSize = getBlockSize();
		int mk = in.length;
		if (in.length % blockSize != 0) {
			mk = in.length + blockSize - (in.length % blockSize);
		} else {
			mk = in.length;
		}
		// mk=mk+blockSize+blockSize;
		byte out[] = new byte[mk];

		byte[] indataB = new byte[mk];

		System.arraycopy(in, 0, indataB, 0, Math.min(in.length, mk));

		for (int k = 0; k < mk / blockSize; k++) {

			processBlock(indataB, k * blockSize, out, k * blockSize);

		}

		return out;
	}

	private byte[] aesDecode(byte in[], byte[] key) throws Exception {
		init(false, key);
		int blockSize = getBlockSize();
		int mk = in.length;
		byte out[] = new byte[mk];
		for (int k = 0; k < (in.length) / blockSize; k++) {
			processBlock(in, k * blockSize, out, k * blockSize);
		}
		int endIndex = mk;
		for (int i = mk - 1; i > -1 && i > -1; i--) {
			if (out[i] != 0)
				break;
			endIndex = i;
		}
		byte outData[] = new byte[endIndex];
		System.arraycopy(out, 0, outData, 0, endIndex);
		return outData;
	}

	public static String encode(String in, String key) throws Exception {
		AES aes = new AES();
		// String b64 = new String(SSOBase64.encode(aes.aesEncode(in
		// .getBytes("UTF-8"), key.getBytes("UTF-8"))));
		// String b64_1 = new String(SSOBase64.encode(aes.aesEncode(in
		// .getBytes("UTF-8"), key.getBytes("UTF-8"))));
		// System.out.println("A::"+b64);
		// System.out.println("B::"+b64_1);
		return new String(SSOBase64.encode(aes.aesEncode(in.getBytes("UTF-8"),
				key.getBytes("UTF-8")))).replaceAll("[\n,\r]", "");

	}

	public static String decode(String in, String key) throws Exception {
		AES aes = new AES();
		byte[] code = SSOBase64.decode(in);

		return new String(aes.aesDecode(code, key.getBytes("UTF-8")));

	}

	public static byte[] encode(byte[] in, byte[] key) throws Exception {
		AES aes = new AES();
		return aes.aesEncode(in, key);

	}

	public static byte[] decode(byte[] in, byte[] key) throws Exception {
		AES aes = new AES();
		return aes.aesDecode(in, key);

	}
}
