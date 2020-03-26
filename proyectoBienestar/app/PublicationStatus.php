<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class PublicationStatus extends Model
{
    protected $table = 'new_statuses';
    protected $fillable = [
        'name',
    ];

    public function publications()
    {
    	return $this->hasMany('App\Publication');
    }
}
