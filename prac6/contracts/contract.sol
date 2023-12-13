// SPDX-License-Identifier: MIT
pragma solidity >=0.7.0 <0.9.0;

struct sellingItem{
    string Name;
    uint   Id;
    uint   Cost;
    uint   Count;
    uint   Time;
    // bool isValue;
}

contract shop{
    // mapping(uint=>uint) buyer;
    // string[] buyer;
    sellingItem[] buyer;
    // mapping(uint=>string) itemlist; // this has been made to get the partcular name of the item through the id
    sellingItem[] itemsForSale; // this is the object of the struct
    address seller; // address of the owner
    // mapping(uint=>uint) cost; // This is used to map b/w the id and the cost .
    constructor(){
        seller = msg.sender;
    }

    modifier onlyOwner(){
        require(seller == msg.sender,"You are not allowed to do this ");
        _;
    }

    function AddItem(string memory _Name , uint _Id, uint _Cost, uint _Count)  public onlyOwner{
        // itemlist[_Id] = _Name;
        // cost[_Id] = _Cost;
        itemsForSale.push(sellingItem(_Name, _Id, _Cost, _Count, block.timestamp));
    }   

    // modifier buyerExists(uint256 _id) {
    //     uint256 index = personIdToIndex[_id]; // is 0 if not explicitly set
    //     require(personIds[index] == _id, "Person does not exist.");
    //     _;
    // }
    function BuyItem(uint _Id, uint _Count) public payable{
        require(itemsForSale[_Id].Count != 0, "The item is out of stock");
        // uint costWei = cost[_Id];
        uint costWei = itemsForSale[_Id].Cost;
        uint costEther = (costWei * 10**18); // This is used to convert the amount into the ether
        uint totalCost = costEther * _Count;
        require(totalCost == msg.value,"Less sufficent amount");
        // buyer.push(itemlist[_Id]);
        if(buyer.length != 0 && buyer[_Id].Count != 0){
            buyer[_Id].Count += _Count;
        } else{
            buyer.push(itemsForSale[_Id]);
        }
        itemsForSale[_Id].Count -= _Count;
    }
 
    // function ViewMyItems() view public returns(string[]memory){
    function ViewMyItems() view public returns(sellingItem[]memory){
        return buyer;
    }

    function GetAllItems() view public returns(sellingItem[]memory){
        return itemsForSale;
    }

    function SellMyItem(uint _Id, uint _Count) public {
        address payable _to = payable(seller);
        _to.transfer(itemsForSale[_Id].Cost);
        if(buyer[_Id].Count < _Count){
            delete buyer[_Id];
        } else{
            buyer[_Id].Count -= _Count;
        }
        itemsForSale[_Id].Count += _Count;
    }
}