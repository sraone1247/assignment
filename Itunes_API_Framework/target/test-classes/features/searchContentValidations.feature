Feature: iTunes Search API Validations
@Regression
Scenario: Validate iTunes Search API Response
	Given user has "iTunesSearchAPI"
	And Query Param term as "Arijit Singh"
	When user hits iTunes Search API
	Then the API call got response with status code 200
	And country in each node should be "USA"
	And response result count should be less than or equal to 50
	And each node in response contains given keyword

@Regression @CountryCode	
Scenario Outline: Validate iTunes Search API Response filtering based on country
	Given user has "iTunesSearchAPI"
	And Query Params term as "Arijit Singh" and "country" as <Country>
	When user hits iTunes Search API
	Then the API call got response with status code 200
	And country in each node should be <Code>
	And response result count should be less than or equal to 50
	And each node in response contains given keyword
		
	Examples:
	| Country | Code |
	| "ca"		| "CAN"|
	| "in"		| "IND"|
	| "ru"		| "RUS"|
	| "au"		| "AUS"|
	
@Regression @MediaKind
Scenario Outline: Validate iTunes Search API Response filtering based on media
	Given user has "iTunesSearchAPI"
	And Query Params term as <Term> and "media" as <Media>
	When user hits iTunes Search API
	Then the API call got response with status code 200
	And response result count should be less than or equal to 50
	And response kind is <Kind>
	And each node in response contains given keyword
	
	Examples:
	|	Term							| 	Media			| 	Kind					|
	|	"James Cameron"		|	"movie"			|	"feature-movie" |
	| "IJlien Havenaar" | "podcast"		| "podcast"				|
	| "A.R. Rahman"			|	"music"			|	"song"					|
	|	"A.R. Rahman"			|	"musicVideo"|	"music-video"		|
	| "Blueprint"				| "audiobook"	| "audiobook"			|
	| "Acoustic"				|	"tvShow"		| "tv-episode"		|
	| "racing games"		| "software"  | "software"			|
	|	"ebook"						| "ebook"			|	"ebook"					|

@Regression @limtResponse
Scenario: Validate iTunes Search API Response with limited no of nodes
	Given user has "iTunesSearchAPI"
	And Query Param term as "Arijit Singh" and "limit" as 6
	When user hits iTunes Search API
	Then the API call got response with status code 200
	And country in each node should be "USA"
	And response result count should be less than or equal to 6
	And each node in response contains given keyword

@Regression @Language
Scenario: Validate iTunes Search API Response with language param
	Given user has "iTunesSearchAPI"
	And Query Params term as "Arijit Singh" and "lang" as <Language>
	When user hits iTunes Search API
	Then the API call got response with status code 200
	And country in each node should be "USA"
	And response result count should be less than or equal to 50
	And each node in response contains given keyword
	
	Examples:
	|	Language	|
	|	"en_us"		|
	| "en_ca"		|
	|	"ja_jp"		|

@Regression @JSONPValidate
Scenario: Validate iTunes Search API Response with callback JSONP.
	Given user has "iTunesSearchAPI"
	And Query Params term as "Arijit Singh" and "callback" as "test"
	When user hits iTunes Search API
	Then the API call got response with status code 200
	And JSONP validation should pass

@Regression @Negative
Scenario: Validate iTunes Search API Response with invalid search String.
	Given user has "iTunesSearchAPI"
	And Query Param term as "^%*(%$#mar"
	When user hits iTunes Search API
	Then the API call got response with status code 200
	And response result count should be less than or equal to 0
	