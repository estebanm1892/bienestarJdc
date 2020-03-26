<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class New extends Model
{
    protected $table = 'news';
    protected $fillable = [
        'tittle', 'content', 'image', 'new_status_id', 'area_id',
    ];

    public function newStatus()
    {
    	return $this->belongsTo('App\NewStatus');
    }

    public function area()
    {
    	return $this->belongsTo('App\Area');
    }
}
