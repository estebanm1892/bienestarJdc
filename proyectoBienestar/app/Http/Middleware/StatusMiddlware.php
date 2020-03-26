<?php

namespace App\Http\Middleware;

use Closure;
use Auth;
class StatusMiddlware
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        if (auth()->check() && auth()->user()->user_status_id == 2) {

            // usuario con sesión iniciada pero inactivo

            // cerramos su sesión
            Auth::guard()->logout();

            // invalidamos su sesión
            $request->session()->invalidate();

            return redirect('logincms')->with('session_msg', 'Ha ocurrido un error, por favor acercate a la oficina de binenestar para colaborate.');   
        } 

        return $next($request); 
    }
}
