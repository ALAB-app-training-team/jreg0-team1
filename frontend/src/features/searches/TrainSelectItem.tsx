
function TrainSelectItem(){

    return (
    <div className="flex items-center border border-primary/[20%] rounded-2xl py-8 px-4 gap-4">
        <div className="bg-primary rounded aspect-square flex items-center">
            <span className="material-symbols-outlined text-white">train</span>
        </div>
        <div className="flex-col">
            <h3>はやぶさ</h3>
            <h5>1号</h5>
        </div>
        <div className="flex-col">
            <h1>06:32</h1>
            <h5>東京</h5>
        </div>
        <div className="grow h-1 bg-primary/[20%]"></div>
        <div className="flex-col">
            <h1>06:39</h1>
            <h5>上野</h5>
        </div>
        <button className="contained_btn">席を予約する</button>
    </div>
    );
}

export default TrainSelectItem;
