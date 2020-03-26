<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

// Route::get('/', function () {
//     return view('welcome');
// });

Route::get('/', function () {
    return view('login.home');
})->name('/');

Route::get('/logincms', function () {
    return view('login.login');
})->name('logincms');

Route::resource('usuarios', 'UserController')->middleware(['auth', 'status']);

Route::resource('areas', 'AreaController')->middleware(['auth', 'status']);

Route::resource('actividades', 'ActivityController')->middleware(['auth', 'status']);
Route::get('/actividad/{id}/destroy', 'ActivityController@destroy')->name('actividades.destroy')->middleware(['auth', 'status']);

// recursos virtuales

Route::resource('recursos-virtuales', 'VirtualResourceController')->middleware(['auth', 'status']);
Route::get('recursos-virtuales.create/{id}', 'VirtualResourceController@create')->name('recursos-virtuales.create')->middleware(['auth', 'status']);
Route::post('recursos-virtuales.store/{id}', 'VirtualResourceController@store')->name('recursos-virtuales.store')->middleware(['auth', 'status']);
Route::put('recursos-virtuales.update/{id}', 'VirtualResourceController@update')->name('recursos-virtuales.update')->middleware(['auth', 'status']);
Route::get('recursos-virtuales.destroy/{id}', 'VirtualResourceController@destroy')->name('recursos-virtuales.destroy')->middleware(['auth', 'status']);

Route::resource('noticias', 'PublicationController')->middleware(['auth', 'status']);
Route::get('/noticia{id}/destroy', 'PublicationController@destroy')->name('noticias.destroy')->middleware(['auth', 'status']);

Route::get('actividades-preinscripciones/{id}', 'ActivityController@show_preregister')->name('actividades.preregisters')->middleware(['auth', 'status']);

Route::resource('normativas', 'NormativeController')->middleware(['auth', 'status']);
Route::get('/normativas{id}/destroy', 'NormativeController@destroy')->name('normativas.destroy')->middleware(['auth', 'status']);

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');

Route::get('/usuario/{id}/destroy', 'HomeController@destroy')->name('home.usuario.destroy')->middleware(['auth', 'status']);

	Route::get('yolo', function(){
	    $user = new App\NewStatus;
	    $user->name = 'Hola';
	    $user->save();
	    return $user;
	});

	// Route::get('yolo', function(){
	//     $user = new App\User;
	//     $user->name = 'Esteban Monroy ';
	//     $user->email = 'estebanm1892@gmail.com';
	//     $user->password = bcrypt('12345');
	//     $user->phone = '3103106589';
	//     $user->rol = 'Director Unidad';
	//     $user->user_type_id = 1;
	//     $user->user_status_id = 1;
	//     $user->save();
	//     return $user;
	// });

	// Route::get('yolo', function(){
	// 	$academic = new App\AcademicProgram;
	// 	$academic->name = 'Ingeniería de Telecomunicaciones';
	// 	$academic->save();
	// 	return $academic;
	// });