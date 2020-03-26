<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;

class HomeController extends Controller
{
    private $menu_item = 1;
    private $title_page = 'Usuarios';
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
        $this->middleware('status');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Contracts\Support\Renderable
     */
    public function index()
    {
        $users = User::where('user_type_id', 2)
        ->orderBy('created_at', 'DESC')
        ->paginate(10);

        return view('admin.users.index', compact('users'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    public function destroy($id)
    {
        $user = User::findOrFail($id);

        if ($user->profile_image != null) {
            if(file_exists(public_path().str_replace(env('APP_URL'), '/', $user->profile_image))){
                unlink(public_path().str_replace(env('APP_URL'), '/', $user->profile_image));
            }
        }

        $user->delete();

        return redirect()->route('home')->with('session_msg', 'Se ha eliminado correctamente el usuario.');
    }
}
