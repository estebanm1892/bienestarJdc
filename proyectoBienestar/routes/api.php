<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('areas', 'AreaController@index_mobile');
Route::get('area/{id}', 'AreaController@show_mobile');
Route::get('area_information/{id}', 'AreaController@show_mobile_information');

Route::get('activity/{id}', 'ActivityController@show_mobile');
Route::get('activity/{id}', 'ActivityController@show_mobile');
Route::get('vresource/{id}', 'VirtualResourceController@show_mobile');

Route::post('activity/{id}/preregister', 'PreregistrationController@store');

Route::get('publications', 'PublicationController@index_mobile');
Route::get('publication/{id}', 'PublicationController@show_mobile');

Route::get('normatives', 'NormativeController@index_mobile');
Route::get('normative/{id}', 'NormativeController@show_mobile');