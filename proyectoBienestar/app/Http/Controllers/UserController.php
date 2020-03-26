<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Area;
use App\User;
use App\UserStatus;

class UserController extends Controller
{

    private $menu_item = 1;
    private $title_page = 'Usuarios';

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        $statuses = UserStatus::orderBy('name', 'ASC')->get();
        $areas  = Area::orderby('name', 'ASC')->get();

        return view('admin.users.create')
        ->with('statuses', $statuses)
        ->with('areas', $areas)
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $rules = [
            'name'              =>  'required|min:4',
            'email'             =>  'required|string|email|max:255|unique:users',
            'password'          =>  'required|min:6',
            'profile_image.*'   =>  'image|mimes:jpg,jpge,png',
            'area_id'           =>  'required|numeric',
            'user_status_id'    =>  'required|numeric'
        ];

        $this->validate($request, $rules);

        $user = new User();

        $user->name             =   $request->input('name');
        $user->email            =   $request->input('email');
        $user->password         =   bcrypt($request->password);
        $user->profile_image    =   '';
        $user->area_id          =   $request->input('area_id');
        $user->user_status_id   =   $request->input('user_status_id');
        $user->user_type_id     =   2;
        
        $user->save();

        if ($request->file('profile_image')) {
            $file       =   $request->file('profile_image');
            $nameImg    =   'bienestar_usuarios'.$user->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/usuarios/';
            $file->move($path, $nameImg);
            $user->profile_image = '/img/usuarios/'.$nameImg;
            $user->save();
        }

        return redirect()->route('home')->with('session_msg', 'Se ha creado correctamente el usuario.');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $user       =   User::findOrFail($id);   
        $statuses   =   UserStatus::orderby('name', 'ASC')->get();
        $myStatus   =   UserStatus::find($user->user_status_id);
        $areas      =   Area::orderby('name', 'ASC')->get();
        $myArea     =   Area::find($user->area_id);

        return view('admin.users.edit', compact('user'))
        ->with('statuses', $statuses)
        ->with('myStatus', $myStatus)
        ->with('areas', $areas)
        ->with('myArea', $myArea)
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $rules = [
            'name'              =>  'required|min:4',
            // 'email'             =>  'required|string|email|max:255|unique:users',
            // 'password'          =>  'required|min:6',
            'profile_image.*'   =>  'image|mimes:jpg,jpge,png',
            'area_id'           =>  'required|numeric',
            'user_status_id'    =>  'required|numeric'
        ];

        $this->validate($request, $rules);

        $user = User::find($id);

        // if($user->email != $request->email){            
        //     $rules = [
        //         'email' => 'required|string|email|max:255|unique:users'
        //     ];

        //     $this->validate($request, $rules);
        // }

        $user->name             =  $request->input('name');
        // $user->email            =  $request->input('email');
        // $user->password         =  bcrypt($request->password);
        $user->area_id          =  $request->input('area_id');
        $user->user_status_id   =  $request->input('user_status_id');

        $user->save();

        if ($request->file('profile_image')) {
            if ($user->profile_image != null) {
                if(file_exists(public_path().str_replace(env('APP_URL'), '/', $user->profile_image))){
                    unlink(public_path().str_replace(env('APP_URL'), '/', $user->profile_image));
                }
            }
            $file       =   $request->file('profile_image');
            $nameImg    =   'bienestar_usuarios'.$user->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/usuarios/';
            $file->move($path, $nameImg);
            $user->profile_image = '/img/usuarios/'.$nameImg;
            $user->save();
        }

        return redirect()->route('home')->with('session_msg', 'Se ha editado correctamente el usuario.');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        // EL METODO VA EN EL HOMECONTROLLER
    }
}
