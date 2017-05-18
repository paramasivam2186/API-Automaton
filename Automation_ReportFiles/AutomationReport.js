function readXml(url) {
	var xml_response;
	$.ajax({
		type : "GET",
		url : url,
		dataType : "xml",
		async : false,
		success : function(data) {
			xml_response = data;

		},
		error : function() {
			alert("An error occurred while processing XML file.");
		}
	});
	// alert($(xml_response).find('TestSuiteName').text());
	return xml_response;

}

var xml = readXml("Reports.xml");

function parseXml() {
	setTestSuiteName();
	getTestDetail();
	generateSummary();
	populateAllDropDown();
}

function setTestSuiteName() {

	var testSuiteName = $(xml).find('TestSuiteName').text();
	var testDateTime = $(xml).find('TestDateTime').text();
	var testEndTime = $(xml).find('TestEndTime').text();
	var duration = $(xml).find('Duration').text();
	var environment = $(xml).find('Environment').text();

	$("#TestSuiteName").append(testSuiteName);
	$("#TestDateTime").append(testDateTime);
	$("#TestEndTime").append(testEndTime);
	$("#Duration").append(duration);
	$("#Environment").append(environment);

}

function getTestDetail() {

	var totalBrowser = [];
	var totalPassed = [];
	var totalFailed = [];
	var totalTestCase = $(xml).find('ReportTestCase').length;

	$(xml).find('ReportTestCase').each(
			function(index) {
				totalBrowser.push($(xml).find('ReportTestCase').eq(index).attr(
						'Browser'));
			});

	function ArrNoDupe(totalBrowser) {
		var temp = [];
		for ( var i = 0; i < totalBrowser.length; i++)
			temp[totalBrowser[i]] = true;
		var r = [];
		for ( var k in temp)
			r.push(k);
		return r;
	}
	var unique = ArrNoDupe(totalBrowser);
	var uniqBrowser = unique.length;

	$(xml).find('ReportTestCase').each(
			function(index) {

				if ($(xml).find('ReportTestCase').eq(index).attr(
						'TestCaseStatus') == "Passed") {
					totalPassed.push("Passed");
				}

				if ($(xml).find('ReportTestCase').eq(index).attr(
						'TestCaseStatus') == "Failed") {
					totalFailed.push("Failed");
				}
			});

	var passedTC = totalPassed.length;
	var failedTC = totalFailed.length;

	$("#totalTest").append(totalTestCase);
	$("#uniqBrowser").append(uniqBrowser);
	$("#totalPassed").append(passedTC);
	$("#totalFailed").append(failedTC);

}

function getSiteName(siteName) {
	var site = "";
if(siteName=="revel"){
	site = "Revel";
}	else if(siteName == "readerplus"){
		site = "Reader +";
}	else if(siteName == "etext"){
		site = "E Text";
}	else if(siteName == "pulse"){
		site = "Pulse";
}	else if(siteName == "csg"){
		site = "CSG";
		}
	return site;
}

function getTotalWebSite() {
	var numberOfSite = [];
	$(xml).find('ReportTestCase')
			.each(
					function(index) {
						if ($(xml).find('ReportTestCase').eq(index).attr(
								'Website') == 'revel') {
							numberOfSite.push($(xml).find('ReportTestCase').eq(
									index).attr('Website'));

						} else if ($(xml).find('ReportTestCase').eq(index)
								.attr('Website') == 'readerplus') {
							numberOfSite.push($(xml).find('ReportTestCase').eq(
									index).attr('Website'));

						} else if ($(xml).find('ReportTestCase').eq(index)
								.attr('Website') == 'pulse') {
							numberOfSite.push($(xml).find('ReportTestCase').eq(
									index).attr('Website'));

						} else if ($(xml).find('ReportTestCase').eq(index)
								.attr('Website') == 'etext') {
							numberOfSite.push($(xml).find('ReportTestCase').eq(
									index).attr('Website'));

						} else if ($(xml).find('ReportTestCase').eq(index)
								.attr('Website') == 'csg') {
							numberOfSite.push($(xml).find('ReportTestCase').eq(
									index).attr('Website'));

						} 
					});

	numberOfSite = $.unique(numberOfSite);
	return numberOfSite;
}

function getPassed_TC4Site(siteName) {
	var passedTC = 0;
	$(xml).find('ReportTestCase')
			.each(
					function(index) {
						if ($(xml).find('ReportTestCase').eq(index).attr(
								'Website') == siteName) {
							if ($(xml).find('ReportTestCase').eq(index).attr(
									'TestCaseStatus') == 'Passed') {
								passedTC = passedTC + 1;
							}
						}
					});

	return passedTC;
}

function getFailed_TC4Site(siteName) {
	var failedTC = 0;
	$(xml).find('ReportTestCase')
			.each(
					function(index) {
						if ($(xml).find('ReportTestCase').eq(index).attr(
								'Website') == siteName) {
							if ($(xml).find('ReportTestCase').eq(index).attr(
									'TestCaseStatus') == 'Failed') {
								failedTC = failedTC + 1;
							}
						}
					});

	return failedTC;
}

function getTotal_4Individual_Sites(siteName) {
	var totalSites = 0;
	$(xml).find('ReportTestCase')
			.each(
					function(index) {
						if ($(xml).find('ReportTestCase').eq(index).attr(
								'Website') == siteName) {
							totalSites = totalSites + 1;
						}
					});

	return totalSites;
}

function getUniqueTestCase(status) {
	var uniqueTestCase = [];
	$(xml).find('ReportTestCase').each(
			function(index) {
				if ($(xml).find('ReportTestCase').eq(index).attr(
						'TestCaseStatus') == status) {
					uniqueTestCase.push($(xml).find('ReportTestCase').eq(index)
							.attr('TestCase'));
				}
			});
	uniqueTestCase = $.unique(uniqueTestCase);
	return uniqueTestCase;
}

function getUniqueBrowser(status, testcase) {
	var uniqueBrowser = [];
	$(xml).find('ReportTestCase').each(
			function(index) {
				if ($(xml).find('ReportTestCase').eq(index).attr(
						'TestCaseStatus') == status) {
					if (testcase == 'All') {
						uniqueBrowser.push($(xml).find('ReportTestCase').eq(
								index).attr('Browser'));
					} else {
						if ($(xml).find('ReportTestCase').eq(index).attr(
								'TestCase') == testcase) {
							uniqueBrowser.push($(xml).find('ReportTestCase')
									.eq(index).attr('Browser'));
						}
					}

				}
			});
			
	
	uniqueBrowser = $.unique(uniqueBrowser);
	return uniqueBrowser;
}

function getUniqueWebSite(status, testcase, browser) {
	var uniqueWebSite = [];
	$(xml).find('ReportTestCase').each(
			function(index) {
				if ($(xml).find('ReportTestCase').eq(index).attr(
						'TestCaseStatus') == status) {
					if (testcase == 'All') {
						if (browser == 'All') {
							uniqueWebSite.push($(xml).find('ReportTestCase')
									.eq(index).attr('Website'));
						} else {
							if ($(xml).find('ReportTestCase').eq(index).attr(
									'Browser') == browser) {
								uniqueWebSite.push($(xml)
										.find('ReportTestCase').eq(index).attr(
												'Website'));
							}
						}

					} else {
						if ($(xml).find('ReportTestCase').eq(index).attr(
								'TestCase') == testcase) {
							if (browser == 'All') {
								uniqueWebSite.push($(xml)
										.find('ReportTestCase').eq(index).attr(
												'Website'));
							} else {
								if ($(xml).find('ReportTestCase').eq(index)
										.attr('Browser') == browser) {
									uniqueWebSite.push($(xml).find(
											'ReportTestCase').eq(index).attr(
											'Website'));
								}
							}
						}
					}

				}
			});

	uniqueWebSite = $.unique(uniqueWebSite);
	return uniqueWebSite;
}

function getwebsiteImage(website) {
	if (website == "revel") {
		return "<img src='revel.ico' alt='Revel'>"
	} else if (website == "readerplus") {
		return "<img src='readerplus.ico' alt='Reader +'>"
	} else if (website == "etext") {
		return "<img src='etext.ico' alt='E Text'>"
	} else if (website == "pulse") {
		return "<img src='pulse.ico' alt='Pulse'>"
	}  else if (website == "csg") {
		return "<img src='csg.ico' alt='CSG'>"
	}  else {
		return website;
	}

}

function getBrowserImage(brows) {

	if (brows.indexOf("firefox") !=-1) {
		return "<img src='firefox.png' alt='Firefox'>"
	} else if (brows.indexOf("chrome") !=-1) {
		return "<img src='chrome.png' alt='Chrome'>"
	} else if (brows.indexOf("internetexplorer") !=-1) {
		return "<img src='ie.png' alt='Internet Explorer'>"
	} else if (brows.indexOf("safari") !=-1) {
		return "<img src='safari.png' alt='Safari'>"
	} else if (brows.indexOf("ipad") !=-1) {
		return "<img src='ipad.png' alt='iPad'>"
	} else {
		return "";
	}

}

function generateSummary() {

	var totalSites = [];
	/*
	 * Create the Header of Summary Table
	 */

	totalSites = getTotalWebSite();

	var tr = $("<tr></tr>");
	$("<td></td>").html("<h3>Summary Report</h3>").appendTo(tr);

	for ( var i in totalSites) {
		$("<td align='center'></td>").html(
				"<b>" + getSiteName(totalSites[i]) + "</b>").appendTo(tr);
	}
	$(tr).appendTo("#summaryTable");

	/*
	 * Create the Second Row for Total Test Cases
	 */
	var tr = $("<tr></tr>");
	$("<td></td>").html("Total No Test Cases").appendTo(tr);

	for ( var j in totalSites) {
		$("<td align='center'></td>").html(
				getTotal_4Individual_Sites(totalSites[j])).appendTo(tr);
	}
	$(tr).appendTo("#summaryTable");

	/*
	 * Create the Third Row for Total Passed Test Cases
	 */
	var tr = $("<tr class='passed'></tr>");
	$("<td></td>").html("Total No of Test Cases Passed").appendTo(tr);

	for ( var j in totalSites) {
		$("<td align='center'></td>").html(getPassed_TC4Site(totalSites[j]))
				.appendTo(tr);
	}
	$(tr).appendTo("#summaryTable");

	/*
	 * Create the Fourth Row for Total Failed Test Cases
	 */
	var tr = $("<tr class='failed'></tr>");
	$("<td></td>").html("Total No of Test Cases Failed").appendTo(tr);

	for ( var j in totalSites) {
		$("<td align='center'></td>").html(getFailed_TC4Site(totalSites[j]))
				.appendTo(tr);
	}
	$(tr).appendTo("#summaryTable");

	/*
	 * Create Pass Rate
	 */
	var tr = $("<tr></tr>");
	$("<td></td>").html("Pass Rate").appendTo(tr);
	for ( var j in totalSites) {
		$("<td align='center'></td>")
				.html(
						"<b>"
								+ ((getPassed_TC4Site(totalSites[j]) / getTotal_4Individual_Sites(totalSites[j])) * 100)
										.toFixed(2) + "%</b>").appendTo(tr);
	}
	$(tr).appendTo("#summaryTable");

}

function populateAllDropDown() {
	$('<option selected="selected">').val('Select Status').text('Select Status').appendTo(
			'#dpstatus');
	$('<option >').val('Passed').text('Passed').appendTo('#dpstatus');
	$('<option >').val('Failed').text('Failed').appendTo('#dpstatus');

	$('<option selected="selected">').val('All').text('All').appendTo(
			'#dptestcase');

	$('<option selected="selected">').val('All').text('All').appendTo(
			'#dpbroswer');

	$('<option selected="selected" >').val('"All"').text('All').appendTo(
			'#dpwebsite');

	populateTestCase();
	populateBrowser();
	populateWebSite();

	showTable();
}

function populateTestCase() {

	var values = [];
	var dropDownStatus = $('#dpstatus option:selected').text();

	values = getUniqueTestCase(dropDownStatus);

	$('#dptestcase').empty();
	for ( var i in values) {
		$('<option >').val(values[i]).text(values[i]).appendTo('#dptestcase');
	}

	$('<option selected="selected">').val('All').text('All').appendTo(
			'#dptestcase');

	populateBrowser();
	populateWebSite();
}

function populateBrowser() {

	var values = [];
	var dropDownStatus = $('#dpstatus option:selected').text();
	var dropDownTestCase = $('#dptestcase option:selected').text();

	values = getUniqueBrowser(dropDownStatus, dropDownTestCase);

	$('#dpbroswer').empty();
	for ( var i in values) {
		$('<option >').val(values[i]).text(values[i]).appendTo('#dpbroswer');
	}

	$('<option selected="selected">').val('All').text('All').appendTo(
			'#dpbroswer');

	populateWebSite();
}

function populateWebSite() {

	var values = [];
	var dropDownStatus = $('#dpstatus option:selected').text();
	var dropDownTestCase = $('#dptestcase option:selected').text();
	var dropDownbrowser = $('#dpbroswer option:selected').text();

	values = getUniqueWebSite(dropDownStatus, dropDownTestCase, dropDownbrowser);

	$('#dpwebsite').empty();
	for ( var i in values) {
		$('<option >').val(values[i]).text(getSiteName(values[i])).appendTo(
				'#dpwebsite');
	}

	$('<option selected="selected">').val('All').text('All').appendTo(
			'#dpwebsite');
}

function getKeyValueOfFilter() {

	var getAttrValue = [];
	var dropDownStatus = $('#dpstatus option:selected').text();
	var dropDownTestCase = $('#dptestcase option:selected').text();
	var dropDownBrowser = $('#dpbroswer option:selected').text();
	var dropDownWebSite = $('#dpwebsite option:selected').val();

	if (dropDownTestCase == 'All' && dropDownBrowser == 'All'
			&& dropDownWebSite == 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		// Only TestCase is Selected
	} else if (dropDownTestCase != 'All' && dropDownBrowser == 'All'
			&& dropDownWebSite == 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("TestCase");
		getAttrValue.push(dropDownTestCase);

		// Only TestCase and Browser is Selected
	} else if (dropDownTestCase != 'All' && dropDownBrowser != 'All'
			&& dropDownWebSite == 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("TestCaseBrowser");
		getAttrValue.push(dropDownTestCase + dropDownBrowser);

		// Only TestCase,Browser and WebSite is Selected
	} else if (dropDownTestCase != 'All' && dropDownBrowser != 'All'
			&& dropDownWebSite != 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("TestCaseID");
		getAttrValue.push(dropDownTestCase + dropDownWebSite + dropDownBrowser);

		// Only TestCase and WebSite is Selected
	} else if (dropDownTestCase != 'All' && dropDownBrowser == 'All'
			&& dropDownWebSite != 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("TestCaseWebSite");
		getAttrValue.push(dropDownTestCase + dropDownWebSite);

		// Only TestCase and Browser is Selected
	} else if (dropDownTestCase != 'All' && dropDownBrowser != 'All'
			&& dropDownWebSite == 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("TestCaseBrowser");
		getAttrValue.push(dropDownTestCase + dropDownBrowser);

		// Only Browser is Selected
	} else if (dropDownTestCase == 'All' && dropDownBrowser != 'All'
			&& dropDownWebSite == 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("Browser");
		getAttrValue.push(dropDownBrowser);

		// Only WebSite is Selected
	} else if (dropDownTestCase == 'All' && dropDownBrowser == 'All'
			&& dropDownWebSite != 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("Website");
		getAttrValue.push(dropDownWebSite);

		// Only WebSite and Browser is Selected
	} else if (dropDownTestCase == 'All' && dropDownBrowser != 'All'
			&& dropDownWebSite != 'All') {
		getAttrValue.push("TestCaseStatus");
		getAttrValue.push(dropDownStatus);

		getAttrValue.push("BrowserWebSite");
		getAttrValue.push(dropDownBrowser + dropDownWebSite);

	}
	return getAttrValue;

}

function showTable() {
	/*
	 * This function will display the table based on filter selection
	 */
	var getKeyValue = [];

	getKeyValue = getKeyValueOfFilter();

	// alert(getKeyValue[0]+":"+getKeyValue[1]);
	// alert(getKeyValue[2]+":"+getKeyValue[3]);

	var dropDownStatus = $('#dpstatus option:selected').text();
	var dropDownTestCase = $('#dptestcase option:selected').text();
	var dropDownBrowser = $('#dpbroswer option:selected').text();
	var dropDownWebSite = $('#dpwebsite option:selected').text();

	$('#collapseContentHere').empty();

	$(xml)
			.find('ReportTestCase')
			.each(
					function(index) {
						if ($(xml).find('ReportTestCase').eq(index).attr(
								getKeyValue[0]) == getKeyValue[1]) {
							if ($(xml).find('ReportTestCase').eq(index).attr(
									getKeyValue[2]) == getKeyValue[3]) {
								/*
								 * collect value for Header
								 */

								var videoURL = "";
								var jsonLink = "";
								var pannelHeading;
								var hdr_TestCase = $(xml)
										.find('ReportTestCase').eq(index).attr(
												'TestCase');
								var hdr_Browser = getBrowserImage($(xml).find(
										'ReportTestCase').eq(index).attr(
										'Browser'));
								var hdr_WebSite = getwebsiteImage($(xml).find(
										'ReportTestCase').eq(index).attr(
										'Website'));
								var hdr_Videojs = $(xml).find('ReportTestCase')
										.eq(index).attr('TestVideoURL');
								var testCaseID = $(xml).find('ReportTestCase')
										.eq(index).attr('TestCaseID');

								var testCaseHeader = hdr_TestCase + " <span>"
										+ hdr_Browser
										+ "</span> <span align='right'>"
										+ hdr_WebSite + "</span>";
								var hdr_jsonLink = $(xml)
										.find('ReportTestCase').eq(index).attr(
												'BrowserWebSite');

								if (hdr_Videojs != "") {
									videoURL = '<a onclick=openVideoURL("'
											+ hdr_Videojs
											+ '")><img width="32px" src="icon_video.png" alt="Load Video"></a>';
									/* videoURL = "&nbsp;"; */

								} else {
									videoURL = "&nbsp;";
								}
								/*
								 * if (hdr_jsonLink != "") { jsonLink = '<a
								 * onclick=openJsonURL("' + hdr_jsonLink + '")><img
								 * height="32px" src="json_file.png" alt="Load
								 * Json"></a>';
								 *  } else { jsonLink = "&nbsp;"; }
								 */
								if ($(xml).find('ReportTestCase').eq(index)
										.attr('TestCaseStatus') == "Failed") {
									pannelHeading = "panel-heading-failed"
								} else {
									pannelHeading = "panel-heading-passed"
								}

								testCaseHeader = hdr_TestCase + " "
										+ hdr_Browser + " " + hdr_WebSite;

								var tableRow = "";
								var ScreenshotLink = "";
								var ss_ScreenshotLink = "";
								$(xml)
										.find('reportTestStep')
										.each(
												function(index1) {
													// alert(testCaseID+"
													// "+$(xml).find('reportTestStep').eq(index1).attr("TestStepID"));
													if ($(xml).find(
															'reportTestStep')
															.eq(index1)
															.attr("TestStepID") == testCaseID) {
														var tr = "";
														if ($(xml)
																.find(
																		'reportTestStep')
																.eq(index1)
																.attr("Type") == "teststep") {
															var desc = $(xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"Description");
															var status = $(xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"Result");
															
															var Screenshot = $(
																	xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"ImageName");

															if (typeof Screenshot === 'undefined') {
																ScreenshotLink = "&nbsp;";

															} else if (Screenshot == '') {
																ScreenshotLink = "&nbsp;";
															}

															else {
																ScreenshotLink = '<a onclick=openScreenShot("'
																		+ Screenshot
																		+ '")><img height="20px" src="icon_camera.png" alt="ScreenShot"></a>';

															}

															var testdata = $(
																	xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"TestData");

															tr = "<tr class='step'><td width='35%'>"
																	+ desc
																	+ "</td><td width='35%'></td><td width='10%' align='center'>"
																	+ status
																	+ "</td><td width='20%' align='left'>"
																	+ testdata
																	+ "</td><td width='20%' align='center'>"
																	+ ScreenshotLink
																	+ "</td></tr>";
														} else {
															var ss_desc = $(xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"Description");
															var ss_status = $(
																	xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"Result");
															/* TODO:Yellesh */

															var ss_Screenshot = $(
																	xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"ImageName");

															if (typeof ss_Screenshot === 'undefined') {
																ss_ScreenshotLink = "&nbsp;";
															}

															else if (ss_Screenshot == '') {
																ss_ScreenshotLink = "&nbsp;";
															}

															else {

																ss_ScreenshotLink = '<a onclick=openScreenShot("'
																		+ ss_Screenshot
																		+ '")><img height="20px" src="icon_camera.png" alt="ScreenShot"></a>';

															}

															var testdata = $(
																	xml)
																	.find(
																			'reportTestStep')
																	.eq(index1)
																	.attr(
																			"TestData");

															tr = "<tr class='substep'><td width='35%'></td><td width='35%'>"
																	+ ss_desc
																	+ "</td><td width='10%' align='center'>"
																	+ ss_status
																	+ "</td><td width='20%' align='left'>"
																	+ testdata
																	+ "</td><td width='20%' align='center'>"
																	+ ss_ScreenshotLink
																	+ "</td></tr>";
														}
														tableRow = tableRow
																+ "" + tr;
													}

												});

								var newCollapse = $(""
										+ "<div class='panel panel-default'>"
										+ "<div class='panel-heading "
										+ pannelHeading
										+ "'>"
										+ "<h4 class='panel-title'>"
										+ "<a class='accordion-toggle' data-toggle='collapse' data-parent='#accordion'  href='#collapse"
										+ index
										+ "'>"
										+ ""
										+ testCaseHeader
										+ ""
										+ " </a> "
										+ videoURL
										+ ""
										+ "</h4>"
										+ "</div>"
										+

										"<div id='collapse"
										+ index
										+ "' class='panel-collapse collapse'>"
										+ "<div class='panel-body'><table id='stepSubStepTable' class='table-bordered'"
										+ "style='margin-left: 20px; margin-top: 12px;' width='95%'>"
										+ "<tr class='tableHdr'><td align='center'>Test Step</td>"
										+ "<td align='center'>Sub Step</td>"
										+ "<td align='center'>Status</td>"
										+ "<td align='center'>Test-Data</td>"
										+ "<td align='center'>Screenshot</td></tr>"
										+ tableRow
										+ "</table>"
										+ "</div></div></div>");

							}
						}

						$(newCollapse).appendTo("#collapseContentHere");
					});

}

function openVideoURL(url) {if ($(xml).find('Environment').text() == "local"){
	window
			.open(
					url,
					url,
					"width=710,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,copyhistory=no,resizable=no");
					}else {
	window
			.open(
					url,
					url,
					"width=710,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,copyhistory=no,resizable=no");
					}
	
}


function openScreenShot(url) {
	window
			.open(
					url,
					url,
					"width=710,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,copyhistory=no,resizable=no");
}
